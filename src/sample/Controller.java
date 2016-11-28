package sample;

/**
 * Created by fredcolin079 on 28.11.16.
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import javax.management.StringValueExp;
import javax.net.ssl.SNIHostName;
import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
public class Controller implements Initializable {

    @FXML
    private VBox Main = new VBox(); // initialize from fxml this pane
    @FXML
    private GridPane mainPane ;  // initialize from
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        try {
            ArrayList<int[]> InputArrays = new ArrayList<>(); // put for ArrayList  all input arrays
            LoadDataFromFile(InputArrays); // load from file all data & input to our list
            Label[][] result = StartSorting(InputArrays);
            mainPane.setHgap(7);
            mainPane.setVgap(InputArrays.size());
            mainPane.setPadding(new Insets(30,0,0,0));
            Constuctor(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    /** this methode created for put  array for our given ArrayList<Int[]> */
    public void LoadDataFromFile(ArrayList<int[]> list) throws Exception{
        Scanner inFile = new Scanner(new File("data.txt"));
        while(inFile.hasNextLine()){
            String line = inFile.nextLine(); // read each line
            String data[] = line.split(" "); // split it for ' '
            int[] array = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();// contained it for int[] 'array'
            list.add(array);}// and add it to our final ArrayList
        System.out.println("load all inputs : [ DONE ]"); // statusOut
    }
    /** this methode created just to see out of our ArrayList<Int[]> */
    public void ShowInputList(ArrayList<int[]> list){
        for(int[] arli : list){
            for(int ar:arli){System.out.print(ar+" ");}
            System.out.println();
        }
    }
    /** this methode
     * take all ArrayList<int[]> on going it for a loop to all
     * sort methodes and save it in table
     * */
    public Label[][] StartSorting(ArrayList<int[]> list) {
        ShowInputList(list);
        Label[][] result = new Label[list.size()][7];
        for(int[] li:list){
            result[list.indexOf(li)][0]= new Label(String.valueOf(li.length)); //  set a input array length
            result[list.indexOf(li)][1]= new Label(SelectionSort(li)); // set time of Selection Sort time
            result[list.indexOf(li)][2]= new Label(RadixSort(li)); // set time of Radix Sort time
            result[list.indexOf(li)][3]= new Label(BubbleSort(li)); // set Bubble Sort time
            result[list.indexOf(li)][4]= new Label(MergeSort(li)); // set a Merge Sort time
            result[list.indexOf(li)][5]= new Label(QuickSort(li,0,li.length-1)); // set a Quick Sort time
            Integer[] listo = new Integer[li.length]; // convert one array int[] to Integer[] a specially for Heap Sort
            for(int i=0;i<li.length;i++){ listo[i]=li[i]; }// converting ....
            result[list.indexOf(li)][6]= new Label(HeapSort(listo)); // set a Heap Sort time

        }
        return result;// Label[][] - Labels it a is a text:' there Label define like a double massive variable'
    }
    /**

     1) this methode i did finally for collect it all , mean all data( Label[][] ) for GridPane
      AND NOW WE CONSTRUCT ALL DATA FOR GRIDPANE LIKE A LEGO .... :)

     */
    public void Constuctor(Label[][] result){
        for(int i = 0;i<result.length;i++){
            for(int j = 0;j<7;j++ ){
                result[i][j].setPrefWidth(130); // style : width
                result[i][j].setPadding(new Insets(15,10,0,0)); // style : padding
                result[i][j].setAlignment(Pos.CENTER); // style : alignment
                mainPane.add(result[i][j],j,i+1);// add for table
            }
        }
    }

    /**
     *         SO THATS ALL ...
     *         BY THE END SHOWN ALL SORTING METHODS
     *
     *
     *        END FOR EXAMPLE ( how to define a execution time 'ms' ) :
     *
     *      '  long StartTime = System.nanoTime();
     *        Double Result = (System.nanoTime()-StartTime) / 1000000.0;
     *        return Result;
     *                          '
     *
     *
     * bellow we code all of sorting algorithms   */

    /** =============   SELECTION - SORT      =====================================*/
    public String SelectionSort(int[] list) {
        int[] arr = list;
        long StartTime = System.nanoTime();
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;//searching for lowest index
                }
            }
            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        double result = (System.nanoTime() - StartTime) / 1000000.0;
        String end = String.valueOf(result);
        return end + " ms";
    }
    /** =============   RADIX - SORT      =====================================*/
    public String  RadixSort(int[] list) {
        int[] old = list;
        long StartTime = System.nanoTime();
        // Loop for every bit in the integers
        for (int shift = Integer.SIZE - 1; shift > -1; shift--) {
            // The array to put the partially sorted array into
            int[] tmp = new int[old.length];
            // The number of 0s
            int j = 0;
            // Move the 0s to the new array, and the 1s to the old one
            for (int i = 0; i < old.length; i++) {
                // If there is a 1 in the bit we are testing, the number will be negative
                boolean move = old[i] << shift >= 0;
                // If this is the last bit, negative numbers are actually lower
                if (shift == 0 ? !move : move) {
                    tmp[j] = old[i];
                    j++;
                } else {
                    // It's a 1, so stick it in the old array for now
                    old[i - j] = old[i];
                }
            }
            // Copy over the 1s from the old array
            for (int i = j; i < tmp.length; i++) {
                tmp[i] = old[i - j];
            }
            // And now the tmp array gets switched for another round of sorting
            old = tmp;
        }
        double result = (System.nanoTime() - StartTime) / 1000000.0;
        String end = String.valueOf(result);
        return end + " ms";
    }
    /** =============   BUBBLE - SORT      =====================================*/
    public String BubbleSort(int[] arr) {
        int[] list = arr;
        long StartTime = System.nanoTime();
        boolean needNextPass = true;

        for (int k = 1; k < list.length && needNextPass; k++) {
            // Array may be sorted and next pass not needed
            needNextPass = false;
            for (int i = 0; i < list.length - k; i++) {
                if (list[i] > list[i + 1]) {
                    // Swap list[i] with list[i + 1]
                    int temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;

                    needNextPass = true; // Next pass still needed
                }
            }
        }
        double result = (System.nanoTime() - StartTime) / 1000000.0;
        String end = String.valueOf(result);
        return end + " ms";
    }
    /** =============   MERGE - SORT      =====================================*/
    public String MergeSort(int[] arr) {
        int[] list = arr;
        long StartTime = System.nanoTime();
        if (list.length > 1) {
            // Merge sort the first half
            int[] firstHalf = new int[list.length / 2];
            System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
            MergeSort(firstHalf);

            // Merge sort the second half
            int secondHalfLength = list.length - list.length / 2;
            int[] secondHalf = new int[secondHalfLength];
            System.arraycopy(list, list.length / 2,
                    secondHalf, 0, secondHalfLength);
            MergeSort(secondHalf);

            // Merge firstHalf with secondHalf into list
            merge(firstHalf, secondHalf, list); // there bello we create a special a method "MERGE"
        }
        double result = (System.nanoTime() - StartTime) / 1000000.0;
        String end = String.valueOf(result);
        return end + " ms";
    }
    public static void merge(int[] list1, int[] list2, int[] temp) {
        int current1 = 0; // Current index in list1
        int current2 = 0; // Current index in list2
        int current3 = 0; // Current index in temp

        while (current1 < list1.length && current2 < list2.length) {
            if (list1[current1] < list2[current2])
                temp[current3++] = list1[current1++];
            else
                temp[current3++] = list2[current2++];
        }
        while (current1 < list1.length)
            temp[current3++] = list1[current1++];

        while (current2 < list2.length)
            temp[current3++] = list2[current2++];
    }
    /** =============   QUICK - SORT      =====================================*/
    public String QuickSort(int[] arr, int first, int last) {
        int[] list = arr;
        long StartTime = System.nanoTime();
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            QuickSort(list, first, pivotIndex - 1);
            QuickSort(list, pivotIndex + 1, last);
        }
        double result = (System.nanoTime() - StartTime) / 1000000.0;
        String end = String.valueOf(result);
        return end + " ms";
    }

    /** Partition the array list[first..last] */
    private static int partition(int[] list, int first, int last) {
        int pivot = list[first]; // Choose the first element as the pivot
        int low = first + 1; // Index for forward search
        int high = last; // Index for backward search

        while (high > low) {
            // Search forward from left
            while (low <= high && list[low] <= pivot)
                low++;
            // Search backward from right
            while (low <= high && list[high] > pivot)
                high--;
            // Swap two elements in the list
            if (high > low) {
                int temp = list[high];
                list[high] = list[low];
                list[low] = temp;
            }
        }
        while (high > first && list[high] >= pivot)
            high--;
        // Swap pivot with list[high]
        if (pivot > list[high]) {
            list[first] = list[high];
            list[high] = pivot;
            return high;
        }
        else {
            return first;
        }
    }
    /** =============   HEAP - SORT      =====================================
     *
     *          there are maybe problem for understanding of Heap Sort
     *          Heap Sort needed by him a specially Class Heap
     *          to great understanding it go for /src/sample/Heap.java
     *
     * */

    public static <E extends Comparable<E>> String HeapSort(E[] list) {
        // Create a Heap of integers
        long StartTime = System.nanoTime();
        Heap<E> heap = new Heap<E>();

        // Add elements to the heap
        for (int i = 0; i < list.length; i++)
            heap.add(list[i]);

        // Remove elements from the heap
        for (int i = list.length - 1; i >= 0; i--)
            list[i] = heap.remove();
        double result = (System.nanoTime() - StartTime) / 1000000.0;
        String end = String.valueOf(result);
        return end + " ms";
    }

}

/**
 *
 *             project was developed by:
 *             Toleberdyyev Alisher
 *             Suleyman Demirel University
 *             2016 november
 *
 *             thanks for coding :) ...
 *
 *
 * */
