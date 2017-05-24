import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   // create n-by-n grid, with all sites blocked
   private int gridCount;
   private int virtualTop;
   private int virtualBottom;
   private int virtualBottom2;
   private int openSitesCount = 0;
   private int[] siteStatus;
   private int[] cornerSites = new int[4];
   private WeightedQuickUnionUF grid;
   public Percolation(int n)   
   {
       gridCount = n;
       virtualTop = n*n;
       virtualBottom = n*n+1;
       cornerSites[0] = xyTo1D(1, 1);
       cornerSites[1] = xyTo1D(1, gridCount);
       cornerSites[2] = xyTo1D(gridCount, 1);
       cornerSites[3] = xyTo1D(gridCount, gridCount);
       
       if (n <= 0) 
       {
           throw new IllegalArgumentException();
       }
       grid = new WeightedQuickUnionUF(n*n+2);
       siteStatus = new int[n*n];    
   }
   
   // open site (row, col) if it is not open already
   public void open(int row, int col) {
       if (isValid(row, col)) {
           int pointer = xyTo1D(row, col);
           int tempPointer;
           if (siteStatus[pointer] == 1) {
               return;
           }
           openSitesCount++;               
           int cornerSiteIndex = -1;
           if (gridCount == 1) {
               siteStatus[0] = 1;
               return;
           }
           for (int i = 0; i < 4; i++) {
               if (cornerSites[i] == pointer) {
                   cornerSiteIndex = i;
                   break;
               }
           }
           if (cornerSiteIndex == 0) {
               tempPointer = xyTo1D(row, col+1);
               if (isOpen(row, col+1)) {
                   grid.union(pointer, tempPointer);
               }        
               tempPointer = xyTo1D(row+1, col);
               if (isOpen(row+1, col)) {
                   grid.union(pointer, tempPointer);
               }
           }
           else if (cornerSiteIndex == 1) {
               tempPointer = xyTo1D(row, col-1);
               if (isOpen(row, col-1)) {
                   grid.union(pointer, tempPointer);
               }
               tempPointer = xyTo1D(row+1, col);
               if (isOpen(row+1, col)) {
                   grid.union(pointer, tempPointer);
               }               
           }
           else if (cornerSiteIndex == 2) {
               tempPointer = xyTo1D(row, col+1);
               if (isOpen(row, col+1)) {
                   grid.union(pointer, tempPointer);
               }
               tempPointer = xyTo1D(row-1, col);
               if (isOpen(row-1, col)) {
                   grid.union(pointer, tempPointer);
               }               
           }
           else if (cornerSiteIndex == 3) {
               tempPointer = xyTo1D(row, col-1);
               if (isOpen(row, col-1)) {
                   grid.union(pointer, tempPointer);
               }
               tempPointer = xyTo1D(row-1, col);
               if (isOpen(row-1, col)) {
                   grid.union(pointer, tempPointer);
               }               
           }
           else {
               if (row == 1) {
                   tempPointer = xyTo1D(row, col-1);
                   if (isOpen(row, col-1)) {
                       grid.union(pointer, tempPointer);
                   }
                   tempPointer = xyTo1D(row, col+1);
                   if (isOpen(row, col+1)) {
                       grid.union(pointer, tempPointer);
                   }
                   tempPointer = xyTo1D(row+1, col);
                   if (isOpen(row+1, col)) {
                       grid.union(pointer, tempPointer);
                   }
                   
               }
               else if (row == gridCount) {
                   tempPointer = xyTo1D(row, col-1);
                   if (isOpen(row, col-1)) {
                       grid.union(pointer, tempPointer);
                   }
                   tempPointer = xyTo1D(row, col+1);
                   if (isOpen(row, col+1)) {
                       grid.union(pointer, tempPointer);
                   }
                   tempPointer = xyTo1D(row-1, col);
                   if (isOpen(row-1, col)) {
                       grid.union(pointer, tempPointer);
                   }
                   
               }
               else {
                   if (col == 1) {
                       tempPointer = xyTo1D(row-1, col);
                       if (isOpen(row-1, col)) {
                           grid.union(pointer, tempPointer);
                       }
                       tempPointer = xyTo1D(row, col+1);
                       if (isOpen(row, col+1)) {
                           grid.union(pointer, tempPointer);
                       }
                       tempPointer = xyTo1D(row+1, col);
                       if (isOpen(row+1, col)) {
                           grid.union(pointer, tempPointer);
                       }
                   }
                   else if (col == gridCount) {
                       tempPointer = xyTo1D(row-1, col);
                       if (isOpen(row-1, col)) {
                           grid.union(pointer, tempPointer);
                       }
                       tempPointer = xyTo1D(row, col-1);
                       if (isOpen(row, col-1)) {
                           grid.union(pointer, tempPointer);
                       }
                       tempPointer = xyTo1D(row+1, col);
                       if (isOpen(row+1, col)) {
                           grid.union(pointer, tempPointer);
                       }
                   }
                   else {
                       tempPointer = xyTo1D(row-1, col);
                       if (isOpen(row-1, col)) {
                           grid.union(pointer, tempPointer);
                       }
                       tempPointer = xyTo1D(row, col+1);
                       if (isOpen(row, col+1)) {
                           grid.union(pointer, tempPointer);
                       }
                       tempPointer = xyTo1D(row+1, col);
                       if (isOpen(row+1, col)) {
                           grid.union(pointer, tempPointer);
                       }
                       tempPointer = xyTo1D(row, col-1);
                       if (isOpen(row, col-1)) {
                           grid.union(pointer, tempPointer);
                       }                       
                   }
                   
               }
           }
           if (row == 1) {
               grid.union(virtualTop, pointer);
           }
           if (row == gridCount) {
        	   if (percolates()) {
        		   grid.union(virtualBottom, pointer);
        	   } else {
        		   grid.union(virtualBottom, pointer);
        	   }
           }
           siteStatus[pointer] = 1;
       }       
   }    
   
   // is site (row, col) open?
   public boolean isOpen(int row, int col) {
       if (isValid(row, col)) {
           if (siteStatus[xyTo1D(row, col)] == 1) {
               return true;
           }
       }       
       return false;
   }  
   
   // is site (row, col) full?
   public boolean isFull(int row, int col) {
       if (isValid(row, col)) {
           return grid.connected(virtualTop, xyTo1D(row, col));
       }       
       return false;
   }  
   
   // number of open sites
   public int numberOfOpenSites() {  
     return openSitesCount;
   } 
   
   // does the system percolate?
   public boolean percolates() {
       return grid.connected(virtualTop, virtualBottom);
    }              
   
   private int xyTo1D(int row, int col) {
       int result = (row-1)*(gridCount)+(col-1);
       return result;
   }
   
   private boolean isValid(int row, int col) {
       if (row <= 0 || col <= 0 || row > gridCount || col > gridCount) {
           throw new IndexOutOfBoundsException();
       }
       else {
           return true;
       }
   }
   
   // test client (optional)
   public static void main(String[] args){
   }   
}