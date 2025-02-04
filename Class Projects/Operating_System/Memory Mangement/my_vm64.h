#include <stddef.h>

#define MAX_MEMSIZE (1UL<<32)
#define MEMSIZE (1UL<<30)
#define TLB_ENTRIES 256


void set_physical_mem();

void * translate(unsigned long long vp);

unsigned long long page_map(unsigned long long vp);

void * t_malloc(size_t n);

int t_free(unsigned long long vp, size_t n);

int put_value(unsigned long long vp, void *val, size_t n);

int get_value(unsigned long long vp, void *dst, size_t n);

void mat_mult(unsigned long long a, unsigned long long b, unsigned long long c, size_t l, size_t m, size_t n);

void add_TLB(unsigned long long vpage, unsigned long long ppage);

int check_TLB(unsigned long long vpage);

void print_TLB_missrate();
