// File:	thread-worker.c

// List all group member's name:
/*
Satya Pandya : srp260
Deep Patel : ddp111 
 */
// username of iLab: ddp111
// iLab Server: ls.cs.rutgers.edu


#include "thread-worker.h"
#include "thread_worker_types.h"

#include <signal.h>
#include <sys/time.h>
#include <string.h>

#define STACK_SIZE 16 * 1024
#define QUANTUM 10 * 1000


// INITIALIZE ALL YOUR OTHER VARIABLES HERE
int init_scheduler_done = 0;
ucontext_t schedulerContext;
ucontext_t mainContext;
int firstTimeCalled = 0;
worker_t threadIdIncrementing = 1;
tcb* mBenchmarkTCB;
tcb* first_tcb = NULL; // Pointer to the first tcb
tcb* current_tcb = NULL; // Pointer to the current tcb
worker_t mainThreadWaitingOnThreadId;
worker_mutex_t* mut;

//To Store return values
typedef struct Node {
    int key;
    void *value;
    struct Node *next;
} Node;

Node* ret_values;

void insert(int key,void *value) {
    
        Node* temp = (Node*)malloc(sizeof(Node));
        temp->key = key;
        temp->value = value;
        temp->next = NULL;
    
    
    if (ret_values == NULL) {
        ret_values = temp;
    } else {
        Node* temp2 = ret_values;
        while(temp2->next != NULL){
            temp2 = temp2->next;
        }
        temp2->next = temp;
    }
}

void remove_Node(int key){
    Node* temp = ret_values;
    Node* prev = NULL;
    
    if(ret_values == NULL){
        return;
    }

    if(temp != NULL && temp->key == key){
        ret_values = temp->next;
        free(temp);
        return;
    }

    while(temp != NULL && temp->key != key)
    {
        prev = temp;
        temp = temp->next;
    }

    if(temp == NULL){
        return;
    }

    prev->next = temp->next;
    free(temp);
}

void *get_Node(int key){
    if(ret_values == NULL){
        return NULL;
    }
    Node* temp = ret_values;
    
    while(temp != NULL)
    {
        if(temp->key == key){
            return temp->value;
        }
        temp = temp->next;
    }
    return NULL;
}

void enqueue(tcb* new_tcb) {
    if (first_tcb == NULL) {
        first_tcb = new_tcb;
        current_tcb = new_tcb;
    } else {
        tcb* temp = first_tcb->next;
        first_tcb->next = new_tcb;
        new_tcb->next = temp;
    }
}

void remove_tcb(worker_t workerId) {
    tcb* prev = NULL;
    tcb* current = first_tcb;

    while (current != NULL) {
        if (current->threadId == workerId) {
            if (prev == NULL) { // If the tcb to be removed is the first one
                first_tcb = current->next;
            } else {
                prev->next = current->next;
            }
            free(current->threadContext.uc_stack.ss_sp);
            free(current);
            return;
        }
        prev = current;
        current = current->next;
    }

    fprintf(stderr, "Data not found in the linked list\n");
}

void print_list() {
    tcb* temp = first_tcb;
    while (temp != NULL) {
        printf("%d ", temp->threadId);
        temp = temp->next;
    }
    printf("\n");
}

// Function to free the memory allocated for the linked list
void free_list() {
    tcb* current = first_tcb;
    tcb* next;
    while (current != NULL) {
        next = current->next;
        free(current);
        current = next;
    }
    first_tcb = NULL;
    current_tcb = NULL;
}

//1 if exists in runqueue, 0 if not
int tcb_exists(worker_t tId) {
    tcb* temp = first_tcb;
    while (temp != NULL) {
        if (temp->threadId == tId) {
            return 1;
        }
        temp = temp->next;
    }
    return 0;
}

void send_to_back() {
    if (first_tcb == NULL || first_tcb->next == NULL) {
        return; // No action needed if there is 0 or 1 node in the list
    }

    tcb* temp = first_tcb;
    first_tcb = first_tcb->next; // Move first_tcb to the next node
    temp->next = NULL; // Detach the first node

    // Find the last node and append the detached node
    tcb* last = first_tcb;
    while (last->next != NULL) {
        last = last->next;
    }
    last->next = temp;
}

void setMainThreadStatusReady() {
    tcb* temp = first_tcb;
    while (temp != NULL) {
        if (temp->threadId == 0) {
            temp->threadStatus = READY;
            return;
        }
        temp = temp->next;
    }
    fprintf(stderr, "Main thread not found in the linked list\n");
}

void switchcontext(int signum){
	
    //Use the print Statement to test if timer goes off
    //printf("The timer has gone off\n");
    swapcontext(&(first_tcb->threadContext),&schedulerContext);
}

/* create a new thread */
int worker_create(worker_t *thread, pthread_attr_t *attr,
                  void *(*function)(void *), void *arg)
{
    // - create Thread Control Block (TCB)
    // - create and initialize the context of this worker thread
    // - allocate space of stack for this thread to run
    // after everything is set, push this thread into run queue and
    // - make it ready for the execution.
    tcb* worker_thread = (tcb*)malloc(sizeof(tcb));
    if (worker_thread == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        exit(1);
    }
    ucontext_t workerContext;
    if (getcontext(&workerContext) < 0)
    {
        perror("getcontext");
        exit(1);
    }
    void *workerStack = malloc(STACK_SIZE);
    if (workerStack == NULL)
    {
        perror("Failed to allocate stack");
        exit(1);
    }
    workerContext.uc_link = NULL;
	workerContext.uc_stack.ss_sp = workerStack;
	workerContext.uc_stack.ss_size = STACK_SIZE;
	workerContext.uc_stack.ss_flags = 0;

    makecontext(&workerContext, (void (*)(void)) function, 1, arg);
    *thread = threadIdIncrementing;
    worker_thread->threadContext = workerContext;
    worker_thread->threadId = threadIdIncrementing++;
    worker_thread->threadStatus = READY;
    worker_thread->next = NULL;
    //printf("INSIDE WORKER_CREATE BEFORE NEW ENQUEUE\n");
    //print_list();
    enqueue(worker_thread);
    //printf("INSIDE WORKER_CREATE\n");
    //print_list();

    if(firstTimeCalled == 0) {
        if (getcontext(&schedulerContext) < 0)
        {
            perror("getcontext");
            exit(1);
        }
        void *schedStack = malloc(STACK_SIZE);
        if (schedStack == NULL)
        {
            perror("Failed to allocate stack");
            exit(1);
        }
        schedulerContext.uc_stack.ss_sp = schedStack;
        schedulerContext.uc_stack.ss_size = STACK_SIZE;
        schedulerContext.uc_stack.ss_flags = 0;
        schedulerContext.uc_link = NULL;
        makecontext(&schedulerContext, (void *)&schedule, 0);
        mBenchmarkTCB = (tcb*)malloc(sizeof(tcb));
        if (mBenchmarkTCB == NULL) {
            fprintf(stderr, "Memory allocation failed\n");
            exit(1);
        }

        void *mStack = malloc(STACK_SIZE);
        if (mStack == NULL)
        {
            perror("Failed to allocate stack");
            exit(1);
        }
        mainContext.uc_link = NULL;
        mainContext.uc_stack.ss_sp = mStack;
        mainContext.uc_stack.ss_size = STACK_SIZE;
        mainContext.uc_stack.ss_flags = 0;

        mBenchmarkTCB->threadContext = mainContext;
        mBenchmarkTCB->threadId = 0;
        mBenchmarkTCB->threadStatus = READY;
        mBenchmarkTCB->next = NULL;
        enqueue(mBenchmarkTCB);
        firstTimeCalled++;
    }

    // Use sigaction to register signal handler
	struct sigaction sa;
	memset (&sa, 0, sizeof (sa));
	sa.sa_handler = &switchcontext;
	sigaction (SIGPROF, &sa, NULL);

	// Create timer struct
	struct itimerval timer;

	// Set up what the timer should reset to after the timer goes off
	timer.it_interval.tv_usec = 0; 
	timer.it_interval.tv_sec = 1;

	// Set up the current timer to go off in 1 second
	// Note: if both of the following values are zero
	//       the timer will not be active, and the timer
	//       will never go off even if you set the interval value
	timer.it_value.tv_usec = 0;
	timer.it_value.tv_sec = 1;

	// Set the timer up (start the timer)
	setitimer(ITIMER_PROF, &timer, NULL);

    swapcontext(&(mBenchmarkTCB->threadContext), &schedulerContext);
    return 0;
}

/* give CPU possession to other user-level worker threads voluntarily */
int worker_yield()
{

    // - change worker thread's state from Running to Ready
    // - save context of this thread to its thread control block
    // - switch from thread context to scheduler context
    //printf("WORKER YIELD CALLED\n");
    //print_list();

    //Not going to change from Running to Ready, trying this way
    swapcontext(&(first_tcb->threadContext), &schedulerContext);
    return 0;

};

/* terminate a thread */
void worker_exit(void *value_ptr)
{
    // - if value_ptr is provided, save return value
    // - de-allocate any dynamic memory created when starting this thread (could be done here or elsewhere)
    if(value_ptr != NULL) {
       insert(first_tcb->threadId,value_ptr);
    }
    if(first_tcb->threadId == mainThreadWaitingOnThreadId) {
        //printf("SETTING MAIN STATUS TO READY\n");
        //printf("first_tcb = %i", first_tcb->threadId);
        setMainThreadStatusReady();
    }

    if(first_tcb->threadStatus==READY && first_tcb->threadId==0)
        //printf("MAIN THREAD CHANGED STATUS TO READY\n");
    printf("LEAVE THIS LINE IN IDK Y\n");
    remove_tcb(first_tcb->threadId);  //frees tcb stack, and tcb. Both are malloc'd
    setcontext(&schedulerContext);

    //Still need to do return value part
    /*
        worker_t workerId -> return value
        remove thread tcb from run_queue
        deallocate memory
    */
}

/* Wait for thread termination */
int worker_join(worker_t thread, void **value_ptr)
{
    if(tcb_exists(thread) == 1){ //tcb exists
        //printf("THREAD with worker_t %d exists", thread);
        first_tcb->threadStatus = BLOCKED;
        mainThreadWaitingOnThreadId = thread;
        worker_yield();
    }
    if(value_ptr != NULL) {
        *value_ptr = get_Node(thread);
        remove_Node(thread);
	    //if(hashmap contains(thread))
		//*value_ptr = hashmape.get(thread)
    }

    //still have to do return value part.

    // - wait for a specific thread to terminate
    // - if value_ptr is provided, retrieve return value from joining thread
    // - de-allocate any dynamic memory created by the joining thread
    return 0;

};

/* initialize the mutex lock */
int worker_mutex_init(worker_mutex_t *mutex,
                      const pthread_mutexattr_t *mutexattr)
{
    //- initialize data structures for this mutex

    
    
    mut = (worker_mutex_t*)malloc(sizeof(worker_mutex_t));
    
    if (mut == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        exit(1);
    }   
    
     mut->lock = 0; 
     mut->thread_ID = -1;
     mutex = mut;
     
     
     
    
     return 0;

};

/* aquire the mutex lock */
int worker_mutex_lock(worker_mutex_t *mutex)
{   
    if (!mutex->lock){
        mutex->lock = 1;
        mutex->thread_ID = first_tcb->threadId;
      
        
    }
    else if (mutex->thread_ID == first_tcb->threadId){
        
        return 0;
    }
    else
    {
        first_tcb->threadStatus = BLOCKED;
        setcontext(&schedulerContext);
        //swapcontext(&(first_tcb->threadContext),&schedulerContext);
    }
    // - use the built-in test-and-set atomic function to test the mutex
    // - if the mutex is acquired successfully, enter the critical section
    // - if acquiring mutex fails, push current thread into block list and
    // context switch to the scheduler thread
    return 0;

};

/* release the mutex lock */
int worker_mutex_unlock(worker_mutex_t *mutex)
{
    if (!mutex->lock){
        return 1;
    }
    else if (mutex->thread_ID == first_tcb->threadId){
        mutex->thread_ID = -1;
        mutex->lock = 0;

        tcb* last = first_tcb;
        while (last != NULL) {
        if(last->threadStatus == BLOCKED && last->threadId != 0){
           
            last->threadStatus = READY;
        }
        last = last->next;

        
        
    }
    }
    

    // - release mutex and make it available again.
    // - put one or more threads in block list to run queue
    // so that they could compete for mutex later.

    return 0;
};

/* destroy the mutex */
int worker_mutex_destroy(worker_mutex_t *mutex)
{
    // - make sure mutex is not being used
    // - de-allocate dynamic memory created in worker_mutex_init
    
    if(!mutex->lock){
        free(mut);
    }
    else
    {
        printf("Mutex in Use\n");
        setcontext(&schedulerContext);
    }
    return 0;
};

/* scheduler */
static void schedule()
{
// - every time a timer interrupt occurs, your worker thread library
// should be contexted switched from a thread context to this
// schedule() function

// - invoke scheduling algorithms according to the policy (RR or MLFQ)

if(first_tcb == NULL) //all threads finished, including main benchmark thread
    return;

// - schedule policy
#ifndef MLFQ
    // Choose RR
    sched_rr();
#else
    // Choose MLFQ
    
#endif
}

static void sched_rr()
{
    // - your own implementation of RR
    // (feel free to modify arguments and return types)
    /*
        Ready
            Change status to RUNNING
            setcontext(worker context)
        Running
            Change status to READY
            Send to back of run queue
            New front of Run queue (can be last one still if only one in run queue)
            Change status to RUNNING
            setcontext(worker context)
        Blocked
            idk, not going to make anything blocked yet.
    */
   if(first_tcb->threadStatus == READY) {
        first_tcb->threadStatus = RUNNING;
        setcontext(&(first_tcb->threadContext));
   }
   else if(first_tcb->threadStatus == RUNNING) {
        first_tcb->threadStatus = READY;
        
        // printf("FIRST THREAD CHANGED FROM RUNNING TO READY\n");
        // printf("FIRST THREAD is : %d\n",first_tcb->threadId);
        send_to_back();
        // printf("AFTER SEND TO BACK\n");
        // printf("FIRST THREAD is : %d\n",first_tcb->threadId);
        // print_list();
        sched_rr();
   }
   else { //BLOCKED THREAD
        //printf("BLOCKED THREAD");
        send_to_back();
        sched_rr();
   }

}

/* Preemptive MLFQ scheduling algorithm */
static void sched_mlfq()
{
    // - your own implementation of MLFQ
    // (feel free to modify arguments and return types)

}

// Feel free to add any other functions you need.
// You can also create separate files for helper functions, structures, etc.
// But make sure that the Makefile is updated to account for the same.
