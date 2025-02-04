#include "my_vm.h"
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <stdint.h>

//TODO: Define static variables and structs, include headers, etc.
#define PAGE_SIZE 8192
#define OFFSET (int)log2(PAGE_SIZE)
#define LEVEL1 (32 - OFFSET)/2
#define LEVEL2 (32 - OFFSET - LEVEL1)
#define PAGE_TABLE_SIZE (PAGE_SIZE / sizeof(pte))
#define PAGES (MEMSIZE / PAGE_SIZE)
#define MIN(a, b) ((a) < (b) ? (a) : (b))
#define OUTER_PT_SIZE (((PAGES - 1) < MIN(1 << LEVEL1, PAGE_TABLE_SIZE)) ? (PAGES - 1) : MIN(1 << LEVEL1, PAGE_TABLE_SIZE))
#define INNER_PT_SIZE ((1 << LEVEL2) < PAGE_TABLE_SIZE ? (1 << LEVEL2) : PAGE_TABLE_SIZE)

unsigned char* physical_mem = NULL;
char* physical_bitmap;
pte* outer_pt = NULL;
tlb_ent* tlb = NULL;
unsigned int misses = 0;
unsigned int total_tlb_usage = 0;
unsigned int old_index_tlb = 0;

unsigned int generate_virtual_address(unsigned int outer_index, unsigned int inner_index, unsigned int offset) {
    return (outer_index << (LEVEL2 + OFFSET)) | (inner_index << OFFSET) | offset;
}

unsigned int first_unset_bit(char *bitmap) {
   // Iterate through each byte of the bitmap
    for (int i = 0; i < PAGES; i++) {
        // Check if the current byte is not equal to 0xFF (all bits set)
        if (get_bit_at_index(bitmap, i) != 0xFF) {
            // Iterate through each bit of the current byte
            for (int j = 0; j < 8; j++) {
                // Check if the j-th bit is unset (0)
                if (!(get_bit_at_index(bitmap, i) & (1 << j))) {
                    // Calculate and return the position of the first unset bit
                    return (i * 8) + j; // Adding 1 to convert to 1-indexed position
                }
            }
        }
    }
    // If no unset bit is found, return 0
    return 0;
}

/*
 * Function 2: SETTING A BIT AT AN INDEX
 * Function to set a bit at "index" bitmap
 */
static void set_bit_at_index(char *bitmap, int index)
{
    //Implement your code here
    if (index >= 0 && index < PAGES) {
        bitmap[index / 8] |= (1 << (index % 8));
    } else {
        printf("Error: Index out of bounds.");
    } 
    
}


/*
 * Function 3: GETTING A BIT AT AN INDEX
 * Function to get a bit at "index"
 */
static int get_bit_at_index(char *bitmap, int index)
{
    //Get to the location in the character bitmap array
    //Implement your code here
    if (index >= 0 && index < PAGES) {
        return (bitmap[index / 8] >> (index % 8)) & 1;
    } else {
        printf("Error: Index out of bounds.");
        return -1; // Return an error value
    }
    
}

static void reset_bit_at_index(char *bitmap, int index){
    // Implement your code here
    if (index >= 0 && index < PAGES) {
        bitmap[index / 8] &= ~(1 << (index % 8));
    } else {
        printf("Error: Index out of bounds.");
    }
}

/*
*  Find free physical memory page
*
*/
static unsigned int get_next_avail() {
    //Find free page, set physical bitmap that page as allocated, return that page number
    int index = first_unset_bit(physical_bitmap);
    set_bit_at_index(physical_bitmap,index);
    return index;

}

/*
* Function to get contiguous free inner page(s)
* Returns the starting page if sequence of free page(s) found, 0 if not (memory not available)
*/
static unsigned int get_avail_inner_pages(int num_pages) {
    //If pages greater then one, find a sequence of contiguous free inner pages, return the first inner page id
    //Return starting page id if mem available, 0 if not enough memory for contigious free inner pages
    //Shd set bit in virtual bitmap if used


    // Loop through the outer page table to find a sequence of contiguous free inner pages
    int consecutive_free_pages = 0;
    for (int i = 0; i < OUTER_PT_SIZE; i++) {
        // Get the inner page table associated with the current outer page table entry
        pte *inner_pt = (pte *)(physical_mem + outer_pt[i].page_number * PAGE_SIZE);
        // Loop through the inner page table to find a sequence of contiguous free pages
            // If the inner page is not allocated, increment the counter
            for(int j = 0; j < INNER_PT_SIZE;j++){
            if (inner_pt[j].page_number == 0) {
                consecutive_free_pages++;
                // If the required number of contiguous free pages is found, mark them as used and return the starting page number
                if (consecutive_free_pages == num_pages) {
                   
                    unsigned int res = (INNER_PT_SIZE * i) + (j - num_pages + 1);
                    //printf("%i %ld %d %d\n",res,INNER_PT_SIZE,i,j);
                    return res;
                }
            } else {
                // If the inner page is allocated, reset the counter
                consecutive_free_pages = 0;
            }
            }
    }

    // Not enough contiguous free pages found
    return -1;


}

void set_physical_mem(){
    //TODO: Finish
    // printf("PAGES: %lu\n", PAGES);
    // printf("PAGES/8: %lu, (PAGES + 7) / 8: %lu\n", PAGES/8, (PAGES + 7) / 8);
    tlb = (tlb_ent*)malloc(TLB_ENTRIES*sizeof(tlb_ent));
    if (tlb == NULL) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < TLB_ENTRIES; i++) {
        tlb[i].va = 0; 
        tlb[i].phys_page = 0; 
    }
    physical_bitmap = (char *)malloc((PAGES + 7) / 8);
    if (physical_bitmap == NULL) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    memset(physical_bitmap, 0, sizeof(char) * (PAGES + 7) / 8);
    physical_mem = (unsigned char*)malloc(MEMSIZE);
    if (physical_mem == NULL) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    
    // Initialize outer level page table, set each entry to inner level page table page
    outer_pt = (pte*)physical_mem;
    set_bit_at_index(physical_bitmap, 0);

    for (int i = 0; i < OUTER_PT_SIZE; i++) {
        outer_pt[i].page_number = i+1; //next free page for inner level page table
        set_bit_at_index(physical_bitmap, i+1);

        pte* inner_pt = (pte*)(physical_mem + (i+1) * PAGE_SIZE); //the start of the i+1th page
        for (int j = 0; j < INNER_PT_SIZE; j++) {
            inner_pt[j].page_number = 0; //setting all of the corresponding ptes to 0, not sure if needed since unsigned int is 0 by default i think
        }
    }
}

void * translate(unsigned int vp){
    //TODO: Finish
    // Extract the outer index, inner index, and offset from the virtual address
    unsigned int outer_index = (vp >> (LEVEL2 + OFFSET)) & ((1 << LEVEL1) - 1);
    unsigned int inner_index = (vp >> OFFSET) & ((1 << LEVEL2) - 1);
    unsigned int offset = vp & ((1 << OFFSET) - 1);

    unsigned int tlb_res = check_TLB(vp);
    if(tlb_res != 0) {
        return (void*)(physical_mem + (tlb_res * PAGE_SIZE) + offset);
    }

    // Check if the outer page table entry is valid
    if (outer_index >= OUTER_PT_SIZE || inner_index >= INNER_PT_SIZE) {
        printf("Outer page or inner page table entry is not valid\n");
        return NULL;
    }

    // Get the page number of the inner page table
    unsigned int inner_pt_page_number = outer_pt[outer_index].page_number;

    // Check if the inner page table entry is valid
    if (inner_pt_page_number == 0) {
        printf("Inner page table entry is not valid, page number is 0\n");
        return NULL;
    }

    // Calculate the physical address using the inner page table entry and offset
    unsigned int physical_mem_page_number = ((pte*)(physical_mem + inner_pt_page_number * PAGE_SIZE))[inner_index].page_number;
    // Check if the physical memory page number is valid
    if (physical_mem_page_number == 0) {
        printf("Physical memory page number is not valid\n");
        return NULL;
    }

    // Calculate the physical address
    void *physical_address = physical_mem + (physical_mem_page_number * PAGE_SIZE) + offset;

    return physical_address;
}

void * translate_only_for_tlb_helper(unsigned int vp){
    //TODO: Finish
    // Extract the outer index, inner index, and offset from the virtual address
    unsigned int outer_index = (vp >> (LEVEL2 + OFFSET)) & ((1 << LEVEL1) - 1);
    unsigned int inner_index = (vp >> OFFSET) & ((1 << LEVEL2) - 1);
    unsigned int offset = vp & ((1 << OFFSET) - 1);

    // Check if the outer page table entry is valid
    if (outer_index >= OUTER_PT_SIZE || inner_index >= INNER_PT_SIZE) {
        printf("Outer page or inner page table entry is not valid\n");
        return NULL;
    }

    // Get the page number of the inner page table
    unsigned int inner_pt_page_number = outer_pt[outer_index].page_number;

    // Check if the inner page table entry is valid
    if (inner_pt_page_number == 0) {
        printf("Inner page table entry is not valid, page number is 0\n");
        return NULL;
    }

    // Calculate the physical address using the inner page table entry and offset
    unsigned int physical_mem_page_number = ((pte*)(physical_mem + inner_pt_page_number * PAGE_SIZE))[inner_index].page_number;
    // Check if the physical memory page number is valid
    if (physical_mem_page_number == 0) {
        printf("Physical memory page number is not valid\n");
        return NULL;
    }

    // Calculate the physical address
    void *physical_address = physical_mem + (physical_mem_page_number * PAGE_SIZE) + offset;

    return physical_address;
}

unsigned int page_map(unsigned int vp){
    //TODO: Finish
    unsigned int outer_index, inner_index, offset;
    // Extract the outer index
    outer_index = (vp >> (LEVEL2 + OFFSET)) & ((1 << LEVEL1) - 1);
    // Extract the inner index
    inner_index = (vp >> OFFSET) & ((1 << LEVEL2) - 1);
    // Extract the offset
    offset = vp & ((1 << OFFSET) - 1);

    // Check if the outer page table entry is valid
    if (outer_index >= OUTER_PT_SIZE || inner_index >= INNER_PT_SIZE) {
        printf("Outer page or inner page table entry is not valid\n");
        return -1;
    }

    unsigned int inner_pt_page_number = outer_pt[outer_index].page_number;

    pte* inner_pt = (pte*)(physical_mem + inner_pt_page_number * PAGE_SIZE);
    if(inner_pt[inner_index].page_number != 0) { //found, returning physical page id
        return inner_pt[inner_index].page_number;
    }

    inner_pt[inner_index].page_number = get_next_avail(); //get_next_avail function shd set bit for physical page it chooses
    return inner_pt[inner_index].page_number;
}

void * t_malloc(size_t n){
    //TODO: Finish
    
    int num_pages;
    if (n < PAGE_SIZE)
        num_pages = 1;
    else {
        if (n % PAGE_SIZE == 0)
            num_pages = n / PAGE_SIZE;
        else {
            num_pages = n / PAGE_SIZE + 1;
        }
    }
    unsigned int inner_pg_num = get_avail_inner_pages(num_pages);
    
    if (inner_pg_num == -1) {
        printf("NOT ENOUGH MEMORY!\n");
        return NULL;
    }
    unsigned int outer_pg_num;
    unsigned int vp;
    for (int i = 0; i < num_pages; i++) {
        outer_pg_num = inner_pg_num / INNER_PT_SIZE;
        inner_pg_num = inner_pg_num % INNER_PT_SIZE;
        vp = generate_virtual_address(outer_pg_num, inner_pg_num, 0);
        page_map(vp);
        inner_pg_num++;
    }
    vp = generate_virtual_address(outer_pg_num, inner_pg_num-num_pages, 0);
    return (void *)vp;
}

int t_free(unsigned int vp, size_t n){
    //TODO: Finish
    // Calculate the number of pages to free based on the size requested
    int num_pages;
    if (n < PAGE_SIZE)
        num_pages = 1;
    else {
        if (n % PAGE_SIZE == 0)
            num_pages = n / PAGE_SIZE;
        else {
            num_pages = n / PAGE_SIZE + 1;
        }
    }

    unsigned int outer_index, inner_index, offset;
    // Extract the outer index, inner index, and offset from the virtual address
    outer_index = (vp >> (LEVEL2 + OFFSET)) & ((1 << LEVEL1) - 1);
    inner_index = (vp >> OFFSET) & ((1 << LEVEL2) - 1);
    offset = vp & ((1 << OFFSET) - 1);

    // Check if the outer page table entry is valid
    if (outer_index >= OUTER_PT_SIZE || inner_index >= INNER_PT_SIZE) {
        printf("Outer page or inner page table entry is not valid\n");
        return -1;
    }

    // Get the page number of the inner page table
    unsigned int inner_pt_page_number = outer_pt[outer_index].page_number;

    // Check if the inner page table entry is valid
    if (inner_pt_page_number == 0) {
        printf("Inner page table entry is not valid, page number is 0\n");
        return -1;
    }

    // Get the physical memory page numbers corresponding to the virtual pages
    pte* inner_pt = (pte*)(physical_mem + inner_pt_page_number * PAGE_SIZE);
    for (int i = 0; i < num_pages; i++) {
        if (inner_pt[inner_index + i].page_number == 0) {
            printf("Error: Page not allocated.\n");
            return -1;
        }

        // Reset the corresponding bit in the physical bitmap
        reset_bit_at_index(physical_bitmap, inner_pt[inner_index + i].page_number);
       
        // Clear the entry in the inner page table
        inner_pt[inner_index + i].page_number = 0;
    }

    return 0;
    

}

int put_value(unsigned int vp, void *val, size_t n) {
    // Translate virtual address to physical address
    void *physical_address = translate(vp);

    // Check if translation failed
    if (physical_address == NULL) {
        printf("Translation failed. Unable to put value.\n");
        return -1;
    }

    // Check if the physical address is within the bounds of allocated memory
    if ((unsigned char*)physical_address < physical_mem || (unsigned char*)physical_address >= physical_mem + MEMSIZE) {
        printf("Physical address is out of bounds\n");
        return -1;
    }

    // Copy the value into physical memory
    memcpy(physical_address, val, n);

    return 0;
}

int get_value(unsigned int vp, void *dst, size_t n){
    //TODO: Finish
    // Translate virtual address to physical address
    void *physical_address = translate(vp);

    // Check if translation failed
    if (physical_address == NULL) {
        printf("Translation failed. Unable to get value.\n");
        return -1;
    }

    // Copy the value from physical memory to the result buffer
    memcpy(dst, physical_address, n);

    return 0;
}

void mat_mult(unsigned int a, unsigned int b, unsigned int c, size_t l, size_t m, size_t n){
    //TODO: Finish
    // Translate virtual addresses to physical addresses
    unsigned int *physical_a = translate(a);
    unsigned int *physical_b = translate(b);
    unsigned int *physical_c = translate(c);

    if (physical_a == NULL || physical_b == NULL || physical_c == NULL) {
        printf("Translation failed. Unable to perform matrix multiplication.\n");
        return;
    }

    // Perform matrix multiplication
    for (size_t i = 0; i < l; i++) {
        for (size_t j = 0; j < n; j++) {
            physical_c[i * n + j] = 0;
            for (size_t k = 0; k < m; k++) {
                physical_c[i * n + j] += physical_a[i * m + k] * physical_b[k * n + j];
            }
        }
    }
}

void add_TLB(unsigned int vpage, unsigned int ppage){
    //TODO: Finish
    unsigned int outer_index = (vpage >> (LEVEL2 + OFFSET)) & ((1 << LEVEL1) - 1);
    // Extract the inner index
    unsigned int inner_index = (vpage >> OFFSET) & ((1 << LEVEL2) - 1);

    unsigned int inner_index_relative_to_total = outer_index * OUTER_PT_SIZE + inner_index;

    for(int i = 0; i < TLB_ENTRIES; i++) {
        if(tlb[i].va == 0) {
            tlb[i].va = inner_index_relative_to_total;
            tlb[i].phys_page = ppage;
            return;
        }
    }
    //no space, have to remove older entry
    if(old_index_tlb == TLB_ENTRIES - 1)
        old_index_tlb = 0;
    tlb[old_index_tlb].va = inner_index_relative_to_total;
    tlb[old_index_tlb].phys_page = ppage;
    old_index_tlb++;
    return;
}

int check_TLB(unsigned int vpage){
    //TODO: Finish
    total_tlb_usage++;
    unsigned int outer_index = (vpage >> (LEVEL2 + OFFSET)) & ((1 << LEVEL1) - 1);
    // Extract the inner index
    unsigned int inner_index = (vpage >> OFFSET) & ((1 << LEVEL2) - 1);

    unsigned int inner_index_relative_to_total = outer_index * OUTER_PT_SIZE + inner_index;
    for(int i = 0; i < TLB_ENTRIES; i++) {
        if(tlb[i].va == inner_index_relative_to_total)
            return tlb[i].phys_page;
    }
    //isn't in tlb yet
    misses++;
    if(translate_only_for_tlb_helper(vpage) == NULL) {
        perror("page doesn't exist in phys mem, segmentation fault");
        exit(EXIT_FAILURE);
    }
    add_TLB(vpage, page_map(vpage));
    return 0;
}

void print_TLB_missrate(){
    //TODO: Finish
    double missrate = ((double)misses)/(double)(total_tlb_usage);
    printf("TLB missrate: %lf, %lf%%\n", missrate, missrate*100);
}
