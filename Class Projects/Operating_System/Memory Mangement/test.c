#include <stdio.h>
#include <stdlib.h>
#include "my_vm.h"

#define MATRIX_SIZE 2

void print_matrix(unsigned int vp) {
    int matrix[MATRIX_SIZE][MATRIX_SIZE];
    get_value(vp, matrix, sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);
    printf("Matrix:\n");
    for (int i = 0; i < MATRIX_SIZE; i++) {
        for (int j = 0; j < MATRIX_SIZE; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}

int main() {
    // Allocate memory for two 2x2 matrices
    set_physical_mem();
    unsigned int matrix_a_vp = (unsigned int)t_malloc(sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);
    unsigned int matrix_b_vp = (unsigned int)t_malloc(sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);
    unsigned int result_vp = (unsigned int)t_malloc(sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);

    
    // Populate the matrices with some values
    int matrix_a[MATRIX_SIZE][MATRIX_SIZE] = {{1, 2}, {3, 4}};
    int matrix_b[MATRIX_SIZE][MATRIX_SIZE] = {{5, 6}, {7, 10}};
    put_value(matrix_a_vp, matrix_a, sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);
    put_value(matrix_b_vp, matrix_b, sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);

    
    // Perform matrix multiplication
    mat_mult(matrix_a_vp, matrix_b_vp, result_vp, MATRIX_SIZE, MATRIX_SIZE, MATRIX_SIZE);

    // Print the matrices and the result
    printf("Matrix A:\n");
    print_matrix(matrix_a_vp);
    printf("\nMatrix B:\n");
    print_matrix(matrix_b_vp);
    printf("\nResult of Matrix Multiplication:\n");
    print_matrix(result_vp);

    // Free allocated memory
    t_free(matrix_a_vp, sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);
    t_free(matrix_b_vp, sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);
    t_free(result_vp, sizeof(int) * MATRIX_SIZE * MATRIX_SIZE);



    unsigned int test1 = t_malloc(19000);
    unsigned int test2 = t_malloc(7000);
    printf("%d\n",test1);
    t_free(test1,9000);

    
    printf("%d\n",test2);
    int a = 2;
    put_value(test2,&a,sizeof(int));
    int res;
    get_value(test2,&res,sizeof(int));
    printf("%d---\n",res);
    t_free(test2,7000);

    for(int i = 0; i < 10;i++){
        unsigned int test3 = t_malloc(1);
        printf("%i\n",test3);
        t_free(test3,1);
        
    }
    print_TLB_missrate();
    return 0;
}
