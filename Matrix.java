
public class Matrix{
  private double[][] matrix;
 /**************************************************************/ 
  public Matrix(int row, int col){
     matrix = new double[row][col];
  }
  
  public Matrix(double[][] mat){
    matrix = new double[mat.length][mat[0].length];
    for(int r=0; r<matrix.length; r++){
      for(int c=0; c<matrix[0].length; c++){
        matrix[r][c] = mat[r][c];
      }
    }  
  }
 /**************************************************************/ 
  public String toString(){
    String stringMat = "";
    for(int r=0; r<matrix.length;r++){
      for(int c=0; c<matrix[0].length;c++){
        stringMat += matrix[r][c]; 
        stringMat +="\t";
        }
          stringMat += "\n";
    }
    return stringMat;
  }
 /**************************************************************/ 
  public Boolean equals(Matrix other){
    boolean rows=true;
    boolean cols=true;
    boolean entries=true;
    
    //checks if both have same # of rows
    if(matrix.length != other.matrix.length){
      rows = false;
      return false;
    }
    //checks if both have same # of cols
    if(matrix[0].length != other.matrix[0].length){
      cols=false;
      return false;
    }
    //checks the matrices for same entries & positions
    for(int r=0; r<matrix.length; r++){
      for(int c=0; c<matrix[0].length; c++){
        if (matrix[r][c] != other.matrix[r][c]){
          entries=false;
          return false;
        }
      }
    }
    if(rows && cols && entries){
      return true;
    }else{
      return false;
    }
  }
/**************************************************************/ 
  //getter method
  public double get(int row, int col){
    return matrix[row-1][col-1];   
  }
 
  //setter method
  public void set(int row, int col, double value){
    matrix[row-1][col-1] = value;
  }
/**************************************************************/  
  //returns # rows 
  public int getNumRows(){
    return matrix.length;
  }
 
  //return # cols
  public int getNumCols(){
    return matrix[0].length;
  }
/**************************************************************/ 
  public Matrix add(Matrix other){
    if(matrix.length != other.matrix.length || matrix[0].length != other.matrix[0].length){
      throw new IllegalArgumentException("add requires matrices with the same number of rows and columns.");
    }
    double addedMat[][] = new double[matrix.length][matrix[0].length];
    for(int r=0; r<matrix.length; r++){
      for(int c=0; c<matrix[0].length; c++){
        addedMat[r][c] = matrix[r][c] + other.matrix[r][c];
      }
    }
    return new Matrix(addedMat);
  }
/**************************************************************/
   public Matrix subtract(Matrix other){
    if(this.matrix.length != other.matrix.length || matrix[0].length != other.matrix[0].length){
      throw new IllegalArgumentException("subtract requires matrices with the same number of rows and columns.");
    }
    double subtractedMat[][] = new double[matrix.length][matrix[0].length];
    for(int r=0; r<matrix.length; r++){
      for(int c=0; c<matrix[0].length; c++){
        subtractedMat[r][c] = matrix[r][c] - other.matrix[r][c];
      }
    }
    return new Matrix(subtractedMat);
  }
/**************************************************************/
   public Matrix multiply (double factor){
     double scaledMat[][] = new double[matrix.length][matrix[0].length];
     for(int r=0; r<matrix.length; r++){
      for(int c=0; c<matrix[0].length; c++){
        scaledMat[r][c] = factor * matrix[r][c];
      }
     }
      return new Matrix(scaledMat);
     }
 /**************************************************************/
     public Matrix multiply (Matrix other){
       if(matrix.length != other.matrix[0].length){
         throw new IllegalArgumentException("multiply requires the # of rows of one matrix to be equal to the # of cols of the other.");
       }
       double multMat[][] = new double[matrix.length][other.matrix[0].length];
       //multiplies corresponding elements in each row and column and adds it to the new matrix
       for(int r=0; r<matrix.length; r++){
         for(int c=0; c<other.matrix[0].length; c++){
           for(int a=0; a<matrix[0].length; a++){
             multMat[r][c]+= matrix[r][a] * other.matrix[a][c];
           }
         }
       }           
       return new Matrix(multMat);   
     }
 /**************************************************************/
     public Matrix negative(){
       //create matrix from 2d array instance variable and use scalar multiplication method from before
       Matrix mat = new Matrix(matrix);
       return mat.multiply(-1.0);
     }
 /**************************************************************/      
     public Matrix transpose(){
       double transposeMat[][] = new double[matrix[0].length][matrix.length];
       for(int r =0; r<matrix[0].length; r++){
         for(int c = 0; c<matrix.length; c++){
           //switch the rows and columns
           transposeMat[r][c] = matrix[c][r];
         }
       }
       return new Matrix(transposeMat);
     }
 /**************************************************************/
     public static Matrix identity(int size){
       double identityMat[][]= new double[size][size];
       for(int r=0; r<size;r++){
         for(int c=0; c<size;c++){
           //puts 1's across the diagonal
           identityMat[r][r]=1;
         }
       }
       return new Matrix(identityMat);      
     }
/**************************************************************/
     public Matrix power(int exponent){
       if(matrix.length != matrix[0].length){
         throw new IllegalArgumentException("power requires a square matrix.");
       }
       if(exponent < 0){
         throw new IllegalArgumentException("power requires a positive exponent.");
       }
       
       double powerMat[][] = new double[matrix.length][matrix[0].length];
       
       if(exponent == 0){
         //identity matrix
         Matrix mat = new Matrix(matrix);
         return mat.identity(matrix.length);      
       }
       if(exponent == 1){
         //return same matrix
         for(int r=0; r<matrix.length; r++){
           for(int c=0; c<matrix[0].length; c++){
             powerMat[r][c] = matrix[r][c];
           }
         }
       }
       if(exponent > 1){      
         Matrix mat = new Matrix(matrix);
         for(int e = 1; e < exponent; e++){
            mat = mat.multiply(this);         
         }
         return mat;
       }
       return new Matrix(powerMat);
     }
/********************************************************************/              

public double determinant(){
  if(matrix.length != matrix[0].length){
    throw new IllegalArgumentException("determinant requires a square matrix");
  }
  if(matrix.length == 1){
    return matrix[0][0];
  }
  if(matrix.length == 2){
    double det = (matrix[0][0]*matrix[1][1]) - (matrix[0][1]*matrix[1][0]);
    return det;
  }
  
    double det = 0.0;
    double sign = 1.0;
    for(int c = 0; c<matrix[0].length; c++){
      if(c != 0 && c % 2 != 0){
        sign *= -1.0;
      }
      det *= (sign * matrix[0][c] * (minor(0,c)).determinant());
      
    }
    return det;
}

public Matrix minor(int row, int col){
    double minor[][] = new double[matrix.length-1][matrix.length-1];
    int mr = -1;
    for(int r = 0; r<matrix.length; r++){
        if(r != row-1){
            mr++;
            int mc = -1;
           for(int c = 0; c< matrix.length; c++){
             if(c != col-1){
               mc++;
               minor[mr][mc] = matrix[r][c];      
             }
           }
         }
       }
       return new Matrix(minor);
     }
}