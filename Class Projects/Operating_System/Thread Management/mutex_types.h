#ifndef MTX_TYPES_H
#define MTX_TYPES_H

#include "thread_worker_types.h"

/* mutex struct definition */
typedef struct worker_mutex_t
{
    /* add something here */
    volatile int lock;
    int thread_ID;


    // YOUR CODE HERE
} worker_mutex_t;

#endif
