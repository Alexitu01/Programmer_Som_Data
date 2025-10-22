// (i)

void main(int n){

    if(n > 4){
        printf("Number cannot be higher than 4");
    } else{
    int arr[4];
    arr[0] = 7;
    arr[1] = 13;
    arr[2] = 9;
    arr[3] = 8;
    int *p;
    arrsum(n, arr, p);

    }
}

void arrsum(int n, int arr[], int *sump) {
    int i;
    for(i = 0; i <= n; i = i+1){
        sump = sump + arr[i];
    }
}


