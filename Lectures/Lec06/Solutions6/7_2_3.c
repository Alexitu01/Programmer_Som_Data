// (iii)

void main()
{
    int arr[7];
    int freq[4];
    freq[0] = 0;
    freq[1] = 0;
    freq[2] = 0;
    freq[3] = 0;

    arr[0] = 1;
    arr[1] = 2;
    arr[2] = 1;
    arr[3] = 1;
    arr[4] = 1;
    arr[5] = 2;
    arr[6] = 0;

    histogram(7, arr, 3, freq);

    int j;
    for (j = 0; j <= 3; j = j + 1)
    {
        print freq[j];
    }
}

void histogram(int n, int ns[], int max, int freq[])
{

    int i;
    for (i = 0; i < n; i = i + 1)
    {
        int x;
        for (x = 0; x <= max; x = x + 1)
        {
            if (ns[i] == x)
            {
                freq[x] = freq[x] + 1;
            }
        }
    }
}
