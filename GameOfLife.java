//Nombre: Yovana Alejandra Hinestroza Lopez
import java.util.Random;

// Clase principal del Juego de la Vida
public class GameOfLife {
    // Atributos de la clase
    public String[][] grid; // Matriz que representa el tablero
    public int width, height, generations, speed; // Ancho, alto, número de generaciones y velocidad

    // Constructor de la clase
    public GameOfLife(int width, int height, int generations, int speed, String pattern) {
        this.width = width;
        this.height = height;
        this.generations = generations;
        this.speed = speed;
        grid = new String[height][width]; // Inicialización de la matriz
        setPattern(pattern); // Establecer el patrón inicial
    }

    // Método para ejecutar el Juego de la Vida
    public void run() throws InterruptedException {
        int decreaseGenerations = generations;
        if (generations == 0) {
            while (generations == 0) {
                System.out.println("Dimension: " + height + "x" + width + " " +
                        "| Velocidad: " + speed + " | Generación: Infinito");
                printGrid();
                updateGrid();
                Thread.sleep(speed);
                pressKey();
            }
        } else {
            while (decreaseGenerations > 0) {
                System.out.println("Dimension: " + height + "x" + width + " " +
                        "| Velocidad: " + speed + " | Generación: " + (generations - decreaseGenerations + 1));
                printGrid();
                updateGrid();
                Thread.sleep(speed);
                decreaseGenerations--;
            }
        }
    }

    // Método para esperar la tecla Enter
    public void pressKey() {
        System.out.println("Presiona la tecla Enter para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    // Método para establecer el patrón inicial en el tablero
    public void setPattern(String pattern) {
        String[] choices = {"◼", " "};
        Random random = new Random();

        if (pattern.equals("rnd")) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = choices[random.nextInt(2)];
                }
            }
        } else {
            int x = 0, y = 0;
            for (char c : pattern.toCharArray()) {
                if (c == '1') {
                    grid[x][y] = "◼";
                    y++;
                } else if (c == '0') {
                    grid[x][y] = " ";
                    y++;
                } else if (c == '#') {
                    x++;
                    y = 0;
                } else {
                    System.out.println("Patrón inválido!");
                    System.exit(0);
                }
            }
        }
    }

    // Método para imprimir el tablero
    public void printGrid() {
        // Códigos de escape ANSI para colores
        String YELLOW = "\u001B[33m"; // Amarillo
        String BLACK = "\u001B[30m"; // Negro
        String RESET = "\u001B[0m"; // Restablecer color

        // Imprimir borde superior
        System.out.print("\u250C");
        for (int i = 0; i < width * 4; i++) {
            System.out.print("\u2500");
        }
        System.out.println("\u2510");

        // Imprimir contenido de la matriz
        for (int y = 0; y < height; y++) {
            System.out.print("\u2502");
            for (int x = 0; x < width; x++) {
                // Utilizar caracteres específicos para representar células vivas y muertas
                if (grid[x][y] != null && grid[x][y].equals("◼")) {
                    System.out.print(YELLOW + "██◼" + RESET); // Célula viva
                } else {
                    // Imprimir un cuadro negro completo para células muertas
                    System.out.print(BLACK + "██◻" + RESET); // Célula muerta
                }
            }
            System.out.println("\u2502");
            // No imprimir línea horizontal después de cada fila
        }

        // Imprimir borde inferior
        System.out.print("\u2514");
        for (int i = 0; i < width * 4; i++) {
            System.out.print("\u2500");
        }
        System.out.println("\u2518");
    }

    // Método para llenar la matriz con un caracter dado
    public static void fillGrid(char[][] grid, char c) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = c;
            }
        }
    }

    // Método para actualizar el tablero según las reglas del Juego de la Vida
    public void updateGrid() {
        String[][] nextGrid = new String[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int neighbors = neighborsCount(x, y);
                if (grid[x][y] != null && grid[x][y].equals("◼")) {
                    if (neighbors == 2 || neighbors == 3) {
                        nextGrid[x][y] = "◼";
                    } else {
                        nextGrid[x][y] = " ";
                    }
                } else {
                    if (neighbors == 3) {
                        nextGrid[x][y] = "◼";
                    } else {
                        nextGrid[x][y] = " ";
                    }
                }
            }
        }
        grid = nextGrid;
    }

    // Método para contar el número de vecinos vivos de una celda
    public int neighborsCount(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && nx < width
                        && ny >= 0
                        && ny < height
                        && grid[nx][ny] != null
                        && grid[nx][ny].equals("◼")) {
                    count++;
                }
            }
        }
        return count;
    }

    // Método principal para ejecutar el Juego de la Vida
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("¡Necesitas ingresar 5 argumentos!");
            System.exit(0);
        }
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int generations = Integer.parseInt(args[2]);
        int speed = Integer.parseInt(args[3]);
        String pattern = args[4];

        if (width == 10 || width == 20 || width == 40 || width == 80) {
        } else {
            System.out.println("Ancho de la cuadrícula inválido!");
            System.exit(0);
        }

        if (height == 10 || height == 20 || height == 40) {
        } else {
            System.out.println("Altura de la cuadrícula inválida!");
            System.exit(0);
        }

        if (speed < 250 || speed > 1000) {
            System.out.println("Velocidad inválida!");
            System.exit(0);
        }

        char[][] grid = new char[height][width];
        fillGrid(grid, '@');

        GameOfLife game = new GameOfLife(width, height, generations, speed, pattern);

        try {
            game.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

