#ifndef TW_TYPES_H
#define TW_TYPES_H

#include <ucontext.h>

typedef unsigned int worker_t;

enum Status { //added
    RUNNING,
    READY,
    BLOCKED
};

typedef struct TCB
{
    /* add important states in a thread control block */
    // thread Id
    worker_t threadId;
    // thread status
    enum Status threadStatus;
    // thread context
    ucontext_t threadContext;
    // thread stack
    stack_t threadStack; //dont know if need
    // thread priority
    int threadPriority; //dont know if need
    // And more ...
    struct TCB* next;
    // YOUR CODE HERE

} tcb;

#endif
