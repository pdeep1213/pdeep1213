#include <stddef.h>

#define MAX_MEMSIZE (1UL<<32)
#define MEMSIZE (1UL<<30)
#define TLB_ENTRIES 256


typedef struct page_table_entry{
    unsigned int page_number;  // Physical page number
} pte;

typedef struct tlb_entry{
    unsigned int va;
    unsigned int phys_page;
} tlb_ent;

unsigned int generate_virtual_address(unsigned int outer_index, unsigned int inner_index, unsigned int offset);

unsigned int first_unset_bit(char *bitmap);

static void set_bit_at_index(char *bitmap, int index);

static int get_bit_at_index(char *bitmap, int index);

static void reset_bit_at_index(char *bitmap, int index);

static unsigned int get_next_avail();

static unsigned int get_avail_inner_pages(int num_pages);

void set_physical_mem();

void * translate(unsigned int vp);

void * translate_only_for_tlb_helper(unsigned int vp);

unsigned int page_map(unsigned int vp);

void * t_malloc(size_t n);

int t_free(unsigned int vp, size_t n);

int put_value(unsigned int vp, void *val, size_t n);

int get_value(unsigned int vp, void *dst, size_t n);

void mat_mult(unsigned int a, unsigned int b, unsigned int c, size_t l, size_t m, size_t n);

void add_TLB(unsigned int vpage, unsigned int ppage);

int check_TLB(unsigned int vpage);

void print_TLB_missrate();
