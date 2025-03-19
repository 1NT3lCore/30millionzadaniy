import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionTable {

    // Перечислимый тип для описания взаимного расположения окружностей
    enum CirclePosition {
        TOUCH,          // Окружности касаются в одной точке
        INTERSECT,      // Окружности пересекаются в двух точках
        COINCIDE,       // Окружности совпадают
        NO_INTERSECTION, // Окружности не пересекаются и не вложены
        FIRST_INSIDE_SECOND, // Первая окружность вложена во вторую
        SECOND_INSIDE_FIRST  // Вторая окружность вложена в первую
    }

    // Класс для работы с окружностями
    static class Circle {
        double x, y, r; // Координаты центра и радиус

        // Конструктор для создания окружности
        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        // Метод для определения взаимного расположения двух окружностей
        public CirclePosition checkPosition(Circle other) {
            // Вычисляем расстояние между центрами окружностей
            double distance = Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));

            // Сравниваем расстояние и радиусы
            if (distance == 0 && this.r == other.r) {
                return CirclePosition.COINCIDE; // Окружности совпадают
            } else if (distance == this.r + other.r || distance == Math.abs(this.r - other.r)) {
                return CirclePosition.TOUCH; // Окружности касаются
            } else if (distance < this.r + other.r && distance > Math.abs(this.r - other.r)) {
                return CirclePosition.INTERSECT; // Окружности пересекаются
            } else if (distance > this.r + other.r) {
                return CirclePosition.NO_INTERSECTION; // Окружности не пересекаются
            } else if (distance + other.r <= this.r) {
                return CirclePosition.SECOND_INSIDE_FIRST; // Вторая окружность вложена в первую
            } else if (distance + this.r <= other.r) {
                return CirclePosition.FIRST_INSIDE_SECOND; // Первая окружность вложена во вторую
            } else {
                throw new IllegalStateException("Невозможно определить взаимное расположение окружностей.");
            }
        }
    }

    public static void main(String[] args) {
        // Задание 1: Табулирование функций
        tabulateFunctions();

        // Задание 2: Нахождение наибольшего отрицательного элемента в двумерном массиве
        int[][] array = {
                { -1, -2, -3 },
                { -4, -5 },
                { -6, -7, -8, -9 },
                { 10, 20, 30 } // Положительные числа не учитываются
        };

        int maxNegative = findMaxNegativeElement(array);
        if (maxNegative != Integer.MIN_VALUE) {
            System.out.println("Наибольший отрицательный элемент: " + maxNegative);
        } else {
            System.out.println("Отрицательных элементов в массиве нет.");
        }

        // Задание 3: Сортировка строк матрицы 3x3
        int[][] matrix = {
                { 5, 3, 8 },
                { 1, 9, 4 },
                { 7, 2, 6 }
        };

        System.out.println("Матрица до сортировки:");
        printMatrix(matrix);

        sortMatrixRows(matrix);

        System.out.println("Матрица после сортировки:");
        printMatrix(matrix);

        // Задание 4: Взаимное расположение окружностей
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите параметры первой окружности (x1, y1, r1):");
        double x1 = scanner.nextDouble();
        double y1 = scanner.nextDouble();
        double r1 = scanner.nextDouble();

        System.out.println("Введите параметры второй окружности (x2, y2, r2):");
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();
        double r2 = scanner.nextDouble();

        // Создаем объекты окружностей
        Circle circle1 = new Circle(x1, y1, r1);
        Circle circle2 = new Circle(x2, y2, r2);

        // Определяем взаимное расположение
        CirclePosition position = circle1.checkPosition(circle2);

        // Вывод результата
        switch (position) {
            case TOUCH:
                System.out.println("Окружности касаются в одной точке.");
                break;
            case INTERSECT:
                System.out.println("Окружности пересекаются в двух точках.");
                break;
            case COINCIDE:
                System.out.println("Окружности совпадают.");
                break;
            case NO_INTERSECTION:
                System.out.println("Окружности не пересекаются и не вложены.");
                break;
            case FIRST_INSIDE_SECOND:
                System.out.println("Первая окружность вложена во вторую.");
                break;
            case SECOND_INSIDE_FIRST:
                System.out.println("Вторая окружность вложена в первую.");
                break;
        }

        // Задание 6: Численное интегрирование методом левых прямоугольников
        System.out.println("Введите начало интервала интегрирования:");
        double a = scanner.nextDouble();
        System.out.println("Введите конец интервала интегрирования:");
        double b = scanner.nextDouble();

        // Табулируем функцию y(x) = e^x - x^3 на интервале [a, b] с 101 точкой
        int n = 101;
        double[] xValues = new double[n];
        double[] yValues = new double[n];
        double step = (b - a) / (n - 1);

        for (int i = 0; i < n; i++) {
            xValues[i] = a + i * step;
            yValues[i] = Math.exp(xValues[i]) - Math.pow(xValues[i], 3);
        }

        // Вычисляем интеграл методом левых прямоугольников
        double integral = leftRectangleIntegration(xValues, yValues, step);

        // Аналитическое значение интеграла для функции y(x) = e^x - x^3
        double exactIntegral = (Math.exp(b) - Math.pow(b, 4) / 4) - (Math.exp(a) - Math.pow(a, 4) / 4);

        // Вывод результатов
        System.out.printf("Значение интеграла методом левых прямоугольников: %.6f%n", integral);
        System.out.printf("Точное значение интеграла: %.6f%n", exactIntegral);
        System.out.printf("Погрешность: %.6f%n", Math.abs(integral - exactIntegral));

        // Задание 7: Преобразование числа в систему счисления с основанием от 2 до 8
        System.out.println("Введите целое число в десятичной системе счисления:");
        int decimalValue = scanner.nextInt();

        System.out.println("Введите основание системы счисления (от 2 до 8):");
        int base = scanner.nextInt();

        if (base < 2 || base > 8) {
            System.out.println("Основание системы счисления должно быть от 2 до 8.");
        } else {
            // Преобразуем число в заданную систему счисления
            String convertedValue = decimalToBase(decimalValue, base);

            // Проверяем результат с помощью Integer.toString
            String checkValue = Integer.toString(decimalValue, base);

            System.out.println("Результат преобразования: " + convertedValue);
            System.out.println("Проверка с помощью Integer.toString: " + checkValue);

            if (convertedValue.equals(checkValue)) {
                System.out.println("Преобразование выполнено верно.");
            } else {
                System.out.println("Ошибка в преобразовании.");
            }
        }

        // Задание 8: Вычисление значения полинома по схеме Горнера
        System.out.println("Введите степень полинома (n):");
        int degree = scanner.nextInt();

        // Создаем массив коэффициентов полинома (n+1 элемент)
        double[] coefficients = new double[degree + 1];
        System.out.println("Введите коэффициенты полинома (от an до a0):");
        for (int i = degree; i >= 0; i--) {
            System.out.print("a" + i + ": ");
            coefficients[i] = scanner.nextDouble();
        }

        System.out.println("Введите значение x:");
        double x = scanner.nextDouble();

        // Вычисляем значение полинома по схеме Горнера
        double polynomialValue = hornerScheme(coefficients, x);

        // Вывод результата
        System.out.printf("Значение полинома в точке x = %.2f: %.6f%n", x, polynomialValue);

        // Задание 9: Проверка телефонных номеров
        System.out.println("Введите строку для проверки федерального номера:");
        scanner.nextLine(); // Очистка буфера
        String federalNumber = scanner.nextLine();

        // Регулярное выражение для федерального номера
        String federalRegex = "(\\+7|8)[\\s\\-]?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}";
        Pattern federalPattern = Pattern.compile(federalRegex);
        Matcher federalMatcher = federalPattern.matcher(federalNumber);

        if (federalMatcher.matches()) {
            System.out.println("Федеральный номер корректен.");
        } else {
            System.out.println("Федеральный номер некорректен.");
        }

        System.out.println("Введите строку для проверки муниципального номера Ростова-на-Дону:");
        String municipalNumber = scanner.nextLine();

        // Регулярное выражение для муниципального номера
        String municipalRegex = "\\b[23]([\\s\\-]?\\d{2}){3}\\b";
        Pattern municipalPattern = Pattern.compile(municipalRegex);
        Matcher municipalMatcher = municipalPattern.matcher(municipalNumber);

        if (municipalMatcher.find()) {
            System.out.println("Муниципальный номер корректен.");
        } else {
            System.out.println("Муниципальный номер некорректен.");
        }

        // Задание 10: Извлечение всех телефонных номеров из строки
        String inputString = "Мои номера 220-30-40 и 8904-378-16-61 не считая служебных";
        System.out.println("\nИсходная строка: " + inputString);

        // Регулярное выражение для федерального номера
        String federalRegexExtract = "(\\+7|8)[\\s\\-]?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}";
        Pattern federalPatternExtract = Pattern.compile(federalRegexExtract);

        // Регулярное выражение для муниципального номера
        String municipalRegexExtract = "\\b[23]([\\s\\-]?\\d{2}){3}\\b";
        Pattern municipalPatternExtract = Pattern.compile(municipalRegexExtract);

        // Поиск федеральных номеров
        System.out.println("\nФедеральные номера:");
        Matcher federalMatcherExtract = federalPatternExtract.matcher(inputString);
        while (federalMatcherExtract.find()) {
            System.out.println(federalMatcherExtract.group());
        }

        // Поиск муниципальных номеров
        System.out.println("\nМуниципальные номера:");
        Matcher municipalMatcherExtract = municipalPatternExtract.matcher(inputString);
        while (municipalMatcherExtract.find()) {
            System.out.println(municipalMatcherExtract.group());
        }

        scanner.close();
    }

    // Задание 1: Табулирование функций
    public static void tabulateFunctions() {
        // Начальное значение x
        double start = Math.PI / 15;
        // Конечное значение x
        double end = Math.PI;
        // Шаг изменения x
        double step = Math.PI / 15;

        // Заголовок таблицы
        System.out.printf("%-10s %-15s %-15s%n", "x", "sin(x)", "e^x / (x * lg(x))");

        // Цикл для вычисления и вывода значений функций
        for (double x = start; x <= end; x += step) {
            // Вычисление sin(x)
            double sinX = Math.sin(x);

            // Вычисление e^x / (x * lg(x))
            double expX = Math.exp(x); // e^x
            double lgX = Math.log10(x); // lg(x) - логарифм по основанию 10
            double functionValue = expX / (x * lgX); // e^x / (x * lg(x))

            // Вывод значений в таблицу с заданными форматами
            System.out.printf("%-10.5f %-15.7e %-15.7e%n", x, sinX, functionValue);
        }
    }

    // Задание 2: Нахождение наибольшего отрицательного элемента в двумерном массиве
    public static int findMaxNegativeElement(int[][] array) {
        int maxNegative = Integer.MIN_VALUE; // Изначально устанавливаем минимальное значение

        // Перебираем все строки массива
        for (int[] row : array) {
            // Перебираем все элементы в строке
            for (int element : row) {
                // Если элемент отрицательный и больше текущего максимального отрицательного
                if (element < 0 && element > maxNegative) {
                    maxNegative = element;
                }
            }
        }

        // Если не нашли отрицательных элементов, возвращаем Integer.MIN_VALUE
        return maxNegative == Integer.MIN_VALUE ? Integer.MIN_VALUE : maxNegative;
    }

    // Задание 3: Сортировка строк матрицы 3x3
    public static void sortMatrixRows(int[][] matrix) {
        for (int[] row : matrix) {
            Arrays.sort(row); // Сортировка строки по возрастанию
        }
    }

    // Метод для вывода матрицы на экран
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    // Задание 6: Метод численного интегрирования левыми прямоугольниками
    public static double leftRectangleIntegration(double[] xValues, double[] yValues, double step) {
        double integral = 0.0;

        // Суммируем значения функции на левых концах интервалов
        for (int i = 0; i < yValues.length - 1; i++) {
            integral += yValues[i] * step;
        }

        return integral;
    }

    // Задание 7: Преобразование десятичного числа в систему счисления с основанием base
    public static String decimalToBase(int decimalValue, int base) {
        if (decimalValue == 0) {
            return "0"; // Ноль в любой системе счисления остается нулем
        }

        StringBuilder result = new StringBuilder();
        boolean isNegative = false;

        // Обработка отрицательных чисел
        if (decimalValue < 0) {
            isNegative = true;
            decimalValue = -decimalValue;
        }

        // Преобразование числа в заданную систему счисления
        while (decimalValue > 0) {
            int remainder = decimalValue % base; // Остаток от деления
            result.insert(0, remainder); // Вставляем остаток в начало строки
            decimalValue /= base; // Делим число на основание
        }

        // Добавляем знак минуса, если число было отрицательным
        if (isNegative) {
            result.insert(0, '-');
        }

        return result.toString();
    }

    // Задание 8: Вычисление значения полинома по схеме Горнера
    public static double hornerScheme(double[] coefficients, double x) {
        double result = coefficients[coefficients.length - 1]; // Начинаем с коэффициента an

        // Вычисляем значение полинома по схеме Горнера
        for (int i = coefficients.length - 2; i >= 0; i--) {
            result = result * x + coefficients[i];
        }

        return result;
    }
}