import java.util.Scanner;

public class Administrador {
    static Scanner s = new Scanner(System.in);

    public static void menuPrincipal() {
        // Cargar categorías y productos desde el archivo principal
        GestionarCategoria.cargarCategoriasYProductosDesdeJson();

        int opcion;

        do {
            System.out.println("\n1. Gestionar Productos");
            System.out.println("2. Gestionar Categorías");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = s.nextInt();
            s.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    menuProductos();
                    break;
                case 2:
                    menuCategorias();
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);

        // Guardar categorías y productos al finalizar
        GestionarCategoria.guardarCategoriasYProductosEnJson();
    }

    public static void menuCategorias() {
        int opcion;
        do {
            System.out.println("\n1. Agregar Categoría");
            System.out.println("2. Mostrar Categorías");
            System.out.println("3. Modificar Categorías");
            System.out.println("4. Eliminar Categorías");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = s.nextInt();

            switch (opcion) {
                case 1:
                    GestionarCategoria.agregarCategoria();
                    break;
                case 2:
                    GestionarCategoria.mostrarCategorias();
                    break;
                case 3:
                    GestionarCategoria.modificarCategoria();
                    break;
                case 4:
                    GestionarCategoria.eliminarCategoria();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    public static void menuProductos() {
        int opcion;
        do {
            System.out.println("\n1. Agregar Producto");
            System.out.println("2. Mostrar Productos");
            System.out.println("3. Modificar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = s.nextInt();

            switch (opcion) {
                case 1:
                    GestionarProductos.agregarProducto();
                    break;
                case 2:
                    GestionarProductos.mostrarProductos();
                    break;
                case 3:
                    // Lógica para modificar productos
                    GestionarProductos.modificarProducto();
                    break;
                case 4:
                    // Lógica para eliminar productos
                    GestionarProductos.eliminarProducto();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }
}
