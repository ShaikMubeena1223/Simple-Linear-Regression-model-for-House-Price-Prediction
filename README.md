# 🏠 Simple Linear Regression model for House Price Prediction

## 📖 Project Description

House Price Prediction is a Java-based Machine Learning project that predicts the estimated price of a house using the **Linear Regression** algorithm. The model is implemented from scratch using Java without relying on any external Machine Learning libraries.

The application reads house data from a CSV file, trains the model using the **Normal Equation**, evaluates its performance, and predicts house prices based on user-provided details such as area, number of bedrooms, number of bathrooms, and house age.

---

## 🎯 Objectives

* Build a House Price Prediction system using Java.
* Implement Linear Regression without external ML libraries.
* Learn matrix operations and mathematical model training.
* Evaluate model performance using regression metrics.
* Predict prices for new house data.

---

## ✨ Features

* Load housing data from a CSV file.
* Train a Linear Regression model.
* Perform matrix calculations manually.
* Predict house prices using trained coefficients.
* Evaluate model using:

  * Mean Squared Error (MSE)
  * Root Mean Squared Error (RMSE)
  * R² Score
* Console-based user interface.

---

## 🛠 Technologies Used

* Java
* Eclipse IDE
* Object-Oriented Programming (OOP)
* CSV File Handling
* Linear Regression
* Matrix Operations

---

## 📁 Project Structure

```text
HousePricePrediction/
│── Main.java
│── House.java
│── data.csv
│── README.md
```

### File Details

**Main.java**

* Loads the dataset.
* Trains the Linear Regression model.
* Evaluates model performance.
* Accepts user input.
* Predicts house prices.

**House.java**

* Represents house information.
* Stores:

  * Area
  * Bedrooms
  * Bathrooms
  * Age
  * Price

**data.csv**

* Contains the training dataset used to build the prediction model.

---

## ⚙️ How It Works

1. Load the house dataset from `data.csv`.
2. Convert the dataset into feature and target matrices.
3. Train the Linear Regression model using the Normal Equation.
4. Calculate evaluation metrics (MSE, RMSE, and R²).
5. Ask the user to enter house details.
6. Predict and display the estimated house price.

---

## 📊 Input Features

* Area (Square Feet)
* Number of Bedrooms
* Number of Bathrooms
* Age of the House

### Output

* Estimated House Price

---

## ▶️ How to Run

1. Clone the repository.

```bash
git clone https://github.com/ShaikMubeena1223/HousePricePrediction.git
```

2. Open the project in Eclipse IDE.

3. Ensure `data.csv` is placed in the project directory.

4. Run `Main.java`.

5. Enter the required house details.

6. View the predicted house price.

---

## 📸 Screenshots

### Project Structure

Add your Eclipse project screenshot here.

```md
![Project Structure](images/House Pridiction1.png)
```

### Console Output

Add your console output screenshot here.

```md
![Console Output](images/House pridiction2.png)
```

### Prediction Result

Add your prediction result screenshot here.

```md
![Prediction Result](images/House prediction2.png)
```

---

## 🚀 Future Enhancements

* Develop a GUI using Java Swing or JavaFX.
* Store house records in a database.
* Add more prediction features.
* Improve accuracy using advanced regression techniques.
* Visualize prediction results with graphs.

---

## 👩‍💻 Author

**Shaik Mubeena**

GitHub: https://github.com/ShaikMubeena1223

---

## ⭐ Acknowledgements

Thank you for visiting this project. If you found it useful, please consider giving the repository a ⭐ on GitHub.
