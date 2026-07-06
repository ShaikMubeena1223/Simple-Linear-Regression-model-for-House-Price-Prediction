import java.io.*;
import java.util.*;

public class Main {

    static List<House> houses = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Load dataset
        loadCSV("data.csv");

        if (houses.isEmpty()) {
            System.out.println("Dataset not loaded.");
            return;
        }

        // Create matrices for training
        double[][] featureMatrix = buildFeatureMatrix(houses);
        double[][] targetMatrix = buildTargetMatrix(houses);

        // Train model using Normal Equation:
        // beta = (X^T X)^-1 X^T Y
        double[][] transposeX = transpose(featureMatrix);
        double[][] xtx = multiply(transposeX, featureMatrix);
        double[][] xtxInverse = inverse(xtx);
        double[][] xty = multiply(transposeX, targetMatrix);
        double[][] beta = multiply(xtxInverse, xty);

        // Print heading
        System.out.println("\n===== HOUSE PRICE PREDICTION USING LINEAR REGRESSION =====");
        System.out.println("Model trained successfully!\n");

        // Print learned equation
        System.out.println("Learned Equation:");
        System.out.printf("Price = %.2f + %.2f*(Area) + %.2f*(Bedrooms) + %.2f*(Bathrooms) + %.2f*(Age)%n",
                beta[0][0], beta[1][0], beta[2][0], beta[3][0], beta[4][0]);

        // Evaluate model on dataset
        double mse = calculateMSE(houses, beta);
        double rmse = Math.sqrt(mse);
        double r2 = calculateR2(houses, beta);

        System.out.println("\n===== MODEL PERFORMANCE =====");
        System.out.printf("MSE  = %.2f%n", mse);
        System.out.printf("RMSE = %.2f%n", rmse);
        System.out.printf("R²   = %.4f%n", r2);

        // Take user input for prediction
        System.out.println("\nEnter new house details:");

        System.out.print("Area (sq ft): ");
        double area = input.nextDouble();

        System.out.print("Bedrooms: ");
        int bedrooms = input.nextInt();

        System.out.print("Bathrooms: ");
        int bathrooms = input.nextInt();

        System.out.print("Age of house: ");
        int age = input.nextInt();

        double predictedPrice = predictPrice(beta, area, bedrooms, bathrooms, age);

        System.out.println("\n===== PREDICTION RESULT =====");
        System.out.printf("Predicted House Price = ₹%.2f%n", predictedPrice);

        input.close();
    }

    // Load data from CSV file
    public static void loadCSV(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            reader.readLine(); // skip header row

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] values = line.split(",");

                if (values.length != 5) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                try {
                    double area = Double.parseDouble(values[0].trim());
                    int bedrooms = Integer.parseInt(values[1].trim());
                    int bathrooms = Integer.parseInt(values[2].trim());
                    int age = Integer.parseInt(values[3].trim());
                    double price = Double.parseDouble(values[4].trim());

                    houses.add(new House(area, bedrooms, bathrooms, age, price));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping bad row: " + line);
                }
            }

            reader.close();
            System.out.println("Dataset loaded successfully!");

        } catch (Exception e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // Build X matrix
    // [1, area, bedrooms, bathrooms, age]
    public static double[][] buildFeatureMatrix(List<House> houseList) {
        double[][] matrix = new double[houseList.size()][5];

        for (int i = 0; i < houseList.size(); i++) {
            House house = houseList.get(i);

            matrix[i][0] = 1;              // intercept
            matrix[i][1] = house.area;
            matrix[i][2] = house.bedrooms;
            matrix[i][3] = house.bathrooms;
            matrix[i][4] = house.age;
        }

        return matrix;
    }

    // Build Y matrix (price)
    public static double[][] buildTargetMatrix(List<House> houseList) {
        double[][] matrix = new double[houseList.size()][1];

        for (int i = 0; i < houseList.size(); i++) {
            matrix[i][0] = houseList.get(i).price;
        }

        return matrix;
    }

    // Predict house price
    public static double predictPrice(double[][] beta, double area, int bedrooms, int bathrooms, int age) {
        return beta[0][0]
                + beta[1][0] * area
                + beta[2][0] * bedrooms
                + beta[3][0] * bathrooms
                + beta[4][0] * age;
    }

    // Calculate MSE
    public static double calculateMSE(List<House> houseList, double[][] beta) {
        double totalError = 0;

        for (House house : houseList) {
            double predicted = predictPrice(beta, house.area, house.bedrooms, house.bathrooms, house.age);
            double error = house.price - predicted;
            totalError += error * error;
        }

        return totalError / houseList.size();
    }

    // Calculate R² score
    public static double calculateR2(List<House> houseList, double[][] beta) {
        double meanPrice = 0;

        for (House house : houseList) {
            meanPrice += house.price;
        }
        meanPrice /= houseList.size();

        double ssTotal = 0;   // total sum of squares
        double ssResidual = 0; // residual sum of squares

        for (House house : houseList) {
            double predicted = predictPrice(beta, house.area, house.bedrooms, house.bathrooms, house.age);

            ssTotal += Math.pow(house.price - meanPrice, 2);
            ssResidual += Math.pow(house.price - predicted, 2);
        }

        return 1 - (ssResidual / ssTotal);
    }

    // Matrix transpose
    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] result = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    // Matrix multiplication
    public static double[][] multiply(double[][] firstMatrix, double[][] secondMatrix) {
        int rows = firstMatrix.length;
        int cols = secondMatrix[0].length;
        int common = firstMatrix[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < common; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return result;
    }

    // Matrix inverse using Gauss-Jordan elimination
    public static double[][] inverse(double[][] matrix) {
        int size = matrix.length;
        double[][] augmented = new double[size][2 * size];

        // Create augmented matrix [A | I]
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                augmented[i][j] = matrix[i][j];
            }
            augmented[i][i + size] = 1;
        }

        for (int i = 0; i < size; i++) {
            double diagonal = augmented[i][i];

            // If diagonal is 0, swap with next row
            if (diagonal == 0) {
                for (int k = i + 1; k < size; k++) {
                    if (augmented[k][i] != 0) {
                        double[] temp = augmented[i];
                        augmented[i] = augmented[k];
                        augmented[k] = temp;
                        diagonal = augmented[i][i];
                        break;
                    }
                }
            }

            // Make diagonal 1
            for (int j = 0; j < 2 * size; j++) {
                augmented[i][j] /= diagonal;
            }

            // Make other rows 0
            for (int row = 0; row < size; row++) {
                if (row != i) {
                    double factor = augmented[row][i];
                    for (int col = 0; col < 2 * size; col++) {
                        augmented[row][col] -= factor * augmented[i][col];
                    }
                }
            }
        }

        // Extract inverse matrix
        double[][] inverseMatrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                inverseMatrix[i][j] = augmented[i][j + size];
            }
        }

        return inverseMatrix;
    }
}