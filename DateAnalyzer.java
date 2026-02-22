import java.util.Scanner;
import java.time.LocalDate;

public class DateAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите день: ");
        int d = scanner.nextInt();
        System.out.print("Введите месяц: ");
        int m = scanner.nextInt();
        System.out.print("Введите год: ");
        int y = scanner.nextInt();

        System.out.println("\n--- Результаты анализа ---");
        System.out.println("День недели: " + getDayOfWeekName(d, m, y));
        System.out.println("Високосный год: " + (isLeap(y) ? "Да" : "Нет"));
        System.out.println("Возраст: " + calculateFullYears(d, m, y));

        System.out.println("\n--- Электронное табло ---");
        printDigitalDate(d, m, y);
    }

    /**
     * Алгоритм определения високосного года.
     * Год високосный, если он делится на 4, но не на 100, либо делится на 400.
     */
    public static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Определение дня недели по алгоритму Зеллера.
     */
    public static String getDayOfWeekName(int d, int m, int y) {
        if (m < 3) {
            m += 12;
            y--;
        }
        int k = y % 100;
        int j = y / 100;

        // Формула Зеллера
        int h = (d + (13 * (m + 1)) / 5 + k + (k / 4) + (j / 4) + (5 * j)) % 7;

        // Массив соответствия (в формуле 0 = Суббота, 1 = Воскресенье и т.д.)
        String[] days = {"Суббота", "Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница"};
        return days[h];
    }

    /**
     * Расчет возраста без использования Period.between
     */
    public static int calculateFullYears(int d, int m, int y) {
        LocalDate now = LocalDate.now();
        int currentY = now.getYear();
        int currentM = now.getMonthValue();
        int currentD = now.getDayOfMonth();

        int age = currentY - y;
        if (currentM < m || (currentM == m && currentD < d)) {
            age--;
        }
        return age;
    }

    /**
     * Вывод даты в стиле электронного табло.
     */
    public static void printDigitalDate(int d, int m, int y) {
        String dateStr = String.format("%02d%02d%04d", d, m, y);

        // Используем точки (.) вместо пробелов внутри цифр для идеального выравнивания.
        // Это гарантирует, что каждый символ занимает ровно одно знакоместо.
        String[][] patterns = {
                {"***", "*.*", "*.*", "*.*", "***"}, // 0
                {"..*", "..*", "..*", "..*", "..*"}, // 1
                {"***", "..*", "***", "*..", "***"}, // 2
                {"***", "..*", "***", "..*", "***"}, // 3
                {"*.*", "*.*", "***", "..*", "..*"}, // 4
                {"***", "*..", "***", "..*", "***"}, // 5
                {"***", "*..", "***", "*.*", "***"}, // 6
                {"***", "..*", "..*", "..*", "..*"}, // 7
                {"***", "*.*", "***", "*.*", "***"}, // 8
                {"***", "*.*", "***", "..*", "***"}  // 9
        };

        for (int row = 0; row < 5; row++) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < dateStr.length(); i++) {
                int digit = Character.getNumericValue(dateStr.charAt(i));

                // Добавляем саму цифру
                line.append(patterns[digit][row]);

                // Добавляем фиксированный отступ (два пробела)
                line.append("  ");

                // Разделитель между ДД, ММ и ГГГГ
                if (i == 1 || i == 3) {
                    line.append("   ");
                }
            }

            // Заменяем точки на пробелы только в момент печати
            System.out.println(line.toString().replace('.', ' '));
        }
    }
}