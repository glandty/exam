import java.util.Arrays;
import java.util.Stack;

public class Sort {
    //插入排序(升序)  时间复杂度O(n^2)  空间复杂度O(1) 稳定排序
    public static void insertSort(int[] array) {
        for (int bound = 1; bound < array.length; bound++) {
            int v= array[bound];
            int cur = bound - 1;
            for (;cur >= 0; cur--) {
                if(array[cur] > v) {
                    array[cur + 1] = array[cur];
                } else {
                    break;
                }
            }
            array[cur + 1] = v;
        }
    }

    //希尔排序   时间复杂度O(n^2) 空间复杂度O(1) 不稳定排序
    public static void shellSort(int [] array) {
        int gap = array.length / 2;
        while(gap > 1) {
            insertSortGap(array, gap);
            gap = gap / 2;
        }
        insertSortGap(array, 1);
    }


    private static void insertSortGap(int[] array, int gap) {
        for (int bound = gap; bound < array.length; bound++) {
            int v= array[bound];
            int cur = bound - gap;
            for (;cur >= 0; cur -= gap) {
                if(array[cur] > v) {
                    array[cur + gap] = array[cur];
                } else {
                    break;
                }
            }
            array[cur + gap] = v;
        }
    }

    //选择排序  时间复杂度O(n^2) 空间复杂度O(1) 不稳定排序
    public static void selectSort(int[] array) {
        for (int bound = 0; bound < array.length; bound++) {
            for (int cur = bound + 1; cur < array.length; cur++) {
                if(array[cur] < array[bound]) {
                    int tmp = array[cur];
                    array[cur] = array[bound];
                    array[bound] = tmp;
                }
            }
        }
    }

    //交换函数
    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    //堆排序 时间复杂度O(N*logN) 空间复杂度O(1) 不稳定排序
    public static void heapSort(int[] array) {
        creatHeap(array);

        for (int i = 0; i < array.length - 1; i++) {
            swap(array,0, array.length - 1 - i);
            shiftDown(array, array.length - i -1, 0);
        }
    }

    private static void creatHeap(int[] array) {
        for (int i = (array.length - 1 - 1) / 2; i >= 0 ; i--) {
            shiftDown(array, array.length, i);
        }
    }

    private static void shiftDown(int[] array, int heapLength, int index) {
        int parent = index;
        int child = 2*parent + 1;
        while(child < heapLength) {
            if(child + 1 < heapLength && array[child+1] > array[child]) {
                child = child + 1;
            }
            if(array[child] > array[parent]) {
                int tmp = array[parent];
                array[parent] = array[child];
                array[child] = tmp;
            } else {
                break;
            }
            parent = child;
            child = 2*parent + 1;
        }
    }

    //冒泡排序 时间复杂度O(N^2)  空间复杂度O(1)  稳定排序
    public static void bubberSort(int[] array) {
        for (int bound = 0; bound < array.length; bound++) {
            for (int cur = array.length - 1; cur > bound; cur--) {
                if(array[cur - 1] > array[cur]) {
                    swap(array,cur - 1, cur);
                }
            }
        }
    }

    //快速排序 时间复杂度O(NlogN) 空间复杂度O(logN)
    public static void quickSort(int[] array) {
        quickSortHelper(array, 0 , array.length - 1);
    }

    private static void quickSortHelper(int[] array, int left, int right) {
        if(left >= right) {
            return;
        }
        int index = partition(array, left, right);
        quickSortHelper(array, left, index - 1);
        quickSortHelper(array, index + 1, right);
    }

    private static int partition(int[] array, int left, int right) {
        int beg = left;
        int end = right;
        int base = array[right];
        while(beg < end) {
            while(beg < end && array[beg] <= base) {
                beg++;
            }
            while (beg < end && array[end] >= base) {
                end--   ;
            }
            swap(array, beg ,end);
        }
        swap(array, beg, right);
        return beg;
    }

    //快速排序的非递归实现 借助栈
    public static void quiclSortByLoop(int[] array) {
        Stack<Integer> stack = new Stack<>();
        stack.push(array.length - 1);
        stack.push(0);
        while(!stack.isEmpty()) {
            int left = stack.pop();
            int right = stack.pop();
            if(left >= right) {
                continue;
            }
            int index = partition(array, left, right);
            stack.push(right);
            stack.push(index + 1);

            stack.push(index - 1);
            stack.push(left);
        }
    }

    //归并排序 时间内复杂度O(N*logN) 空间复杂度O(N) 稳定排序
    public static void merge(int[] array, int low, int mid, int high) {
        int[] output = new int[high - low];
        int outputIndex = 0;
        int cur1 = low;
        int cur2 = mid;
        while(cur1 < mid && cur2 < high) {
            if(array[cur1] <= array[cur2]) {
                output[outputIndex] = array[cur1];
                outputIndex++;
                cur1++;
            } else {
                output[outputIndex] = array[cur2];
                outputIndex++;
                cur2++;
            }
        }
        while(cur1 < mid) {
            output[outputIndex] = array[cur1];
            outputIndex++;
            cur1++;
        }
        while(cur2 < high) {
            output[outputIndex] = array[cur2];
            outputIndex++;
            cur2++;
        }
        for (int i = 0; i < high - low; i++) {
            array[low + i] = output[i];
        }
    }

    //归并排序的非递归实现 通过栈
    public static void mergeSortByLoop(int[] array) {
        for(int gap = 1; gap < array.length; gap *= 2) {
            for (int i = 0; i < array.length; i += 2*gap) {
                int beg = i;
                int mid = i + gap;
                int end = i + 2*gap;
                if (mid > array.length) {
                    mid = array.length;
                }
                if (end > array.length) {
                    end = array.length;
                }
                merge(array, beg, mid, end);

            }
        }
    }

    public static void mergeSort(int[] array) {
        mergeSortHelper(array, 0, array.length);
    }

    private static void mergeSortHelper(int[] array, int low, int high) {
        if(high - low <= 1) {
            return;
        }
        int mid = (low + high) / 2;
        mergeSortHelper(array, low, mid );
        mergeSortHelper(array, mid, high);
        merge(array,low, mid, high);
    }

    public static void main(String[] args) {
        int[] array ={8,6,9,7,1,5,3,4,2};
        //insertSort(array);
        //shellSort(array);
        //selectSort(array);
        //heapSort(array);
        //bubberSort(array);
        //quickSort(array);
        //quiclSortByLoop(array);
        //mergeSort(array);
        mergeSortByLoop(array);
        System.out.println(Arrays.toString(array));
    }
}