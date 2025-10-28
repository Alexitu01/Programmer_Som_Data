// (ii)

void main(int n)
{
    if (n <= 20) {
        int arr[20];
        int *p;
        p = 0;
        squares(n, arr);
        arrsum(n, arr, p);
    } else {
        return;
    }
}

void squares(int n, int arr[])
{
    int i;
    for (i = 0; i < n; ++i)
    {
        arr[i] = i * i;
    }
}

void arrsum(int n, int arr[], int *sump) {
    int i;
    for(i = 0; i < n; ++i){
        sump = sump + arr[i];
    }
    print sump;
}
