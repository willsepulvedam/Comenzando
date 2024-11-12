import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class GestionarProductos {
    private static final Gson gson = new Gson();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String JSON_FILE = "productos_categoria_1.json"; // Nombre del archivo JSON
    private static List<Producto> productos = new ArrayList<>(); // Lista de productos

    public static void leerProductos() {
        File file = new File(JSON_FILE);

        // Si el archivo no existe, crea uno nuevo vacío
        if (!file.exists()) {
            try {
                file.createNewFile(); // Crea el archivo vacío
                System.out.println("Archivo no encontrado. Se ha creado un nuevo archivo vacío: " + JSON_FILE);
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
                return;
            }
        }

        // Luego, carga los productos desde el archivo
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // Si el archivo tiene contenido, lo deserializamos
            if (jsonBuilder.length() > 0) {
                Gson gson = new Gson();
                Type productoListType = new TypeToken<List<Producto>>() {}.getType();
                productos = gson.fromJson(jsonBuilder.toString(), productoListType);
                if (productos == null) {
                    productos = new ArrayList<>(); // Si el archivo está vacío o es inválido, inicializamos la lista
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
        }
    }

    // Método para guardar los productos en el archivo JSON
    public static void guardarProductosEnJson() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(JSON_FILE))) {
            Gson gson = new Gson();
            String json = gson.toJson(productos); // Convertir la lista de productos a JSON
            bw.write(json);
            System.out.println("Productos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los productos: " + e.getMessage());
        }
    }

    // Cargar los productos de una categoría específica
    public static void cargarProductosPorCategoria(Categoria categoria) {
        String nombreArchivo = "productos_categoria_" + categoria.getId() + ".json";
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            if (jsonBuilder.length() > 0) {
                Type tipoListaProductos = new TypeToken<List<Producto>>() {}.getType();
                List<Producto> productos = gson.fromJson(jsonBuilder.toString(), tipoListaProductos);
                categoria.setProductos(productos != null ? productos : new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los productos de la categoría: " + e.getMessage());
        }
    }

    // Guardar los productos de una categoría en su archivo JSON
    public static void guardarProductosPorCategoria(Categoria categoria) {
        String nombreArchivo = "productos_categoria_" + categoria.getId() + ".json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            String json = gson.toJson(categoria.getProductos());
            writer.write(json);
            System.out.println("Productos guardados correctamente en la categoría: " + categoria.getNombre());
        } catch (IOException e) {
            System.out.println("Error al guardar los productos de la categoría: " + e.getMessage());
        }
    }

    // Agregar un producto a una categoría específica
    public static void agregarProducto() {
        System.out.print("Ingrese el ID de la categoría a la que desea agregar un producto: ");
        int categoriaId = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Categoria categoria = GestionarCategoria.buscarCategoriaPorId(categoriaId);
        if (categoria != null) {
            System.out.print("Ingrese el nombre del producto: ");
            String nombreProducto = scanner.nextLine();
            System.out.print("Ingrese el precio del producto: ");
            double precio = scanner.nextDouble();
            scanner.nextLine(); // Consumir salto de línea

            int idProducto = categoria.getProductos().isEmpty() ? 1 : categoria.getProductos().get(categoria.getProductos().size() - 1).getId() + 1;
            Producto producto = new Producto(idProducto, nombreProducto, precio, idProducto, categoria);
            categoria.getProductos().add(producto);

            guardarProductosPorCategoria(categoria);
            System.out.println("Producto agregado exitosamente.");
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    // Mostrar los productos de una categoría
    public static void mostrarProductos() {
        System.out.print("Ingrese el ID de la categoría de la cual desea ver los productos: ");
        int categoriaId = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Categoria categoria = GestionarCategoria.buscarCategoriaPorId(categoriaId);
        if (categoria != null && !categoria.getProductos().isEmpty()) {
            System.out.println("Productos en la categoría: " + categoria.getNombre());
            for (Producto producto : categoria.getProductos()) {
                System.out.println(producto);
            }
        } else {
            System.out.println("No hay productos en esta categoría o la categoría no existe.");
        }
    }

    // Modificar un producto en una categoría
    public static void modificarProducto() {
        System.out.print("Ingrese el ID de la categoría donde se encuentra el producto a modificar: ");
        int categoriaId = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Categoria categoria = GestionarCategoria.buscarCategoriaPorId(categoriaId);
        if (categoria != null) {
            System.out.print("Ingrese el ID del producto a modificar: ");
            int idProducto = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            Producto productoAmodificar = buscarProductoPorId(categoria, idProducto);
            if (productoAmodificar != null) {
                System.out.print("Ingrese el nuevo nombre del producto: ");
                String nuevoNombre = scanner.nextLine();
                System.out.print("Ingrese el nuevo precio del producto: ");
                double nuevoPrecio = scanner.nextDouble();
                scanner.nextLine(); // Consumir salto de línea

                productoAmodificar.setNombre(nuevoNombre);
                productoAmodificar.setPrecio(nuevoPrecio);
                System.out.println("Producto modificado exitosamente.");

                guardarProductosPorCategoria(categoria);
            } else {
                System.out.println("Producto no encontrado.");
            }
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    // Eliminar un producto de una categoría
    public static void eliminarProducto() {
        System.out.print("Ingrese el ID de la categoría donde se encuentra el producto a eliminar: ");
        int categoriaId = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Categoria categoria = GestionarCategoria.buscarCategoriaPorId(categoriaId);
        if (categoria != null) {
            System.out.print("Ingrese el ID del producto a eliminar: ");
            int idProducto = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            Producto productoAeliminar = buscarProductoPorId(categoria, idProducto);
            if (productoAeliminar != null) {
                categoria.getProductos().remove(productoAeliminar);
                System.out.println("Producto eliminado exitosamente.");

                guardarProductosPorCategoria(categoria);
            } else {
                System.out.println("Producto no encontrado.");
            }
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    // Método auxiliar para buscar un producto por ID
    private static Producto buscarProductoPorId(Categoria categoria, int idProducto) {
        for (Producto producto : categoria.getProductos()) {
            if (producto.getId() == idProducto) {
                return producto;
            }
        }
        return null; // Si no se encuentra el producto
    }
}
