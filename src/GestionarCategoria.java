import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionarCategoria {
    private static final String JSON_FILE = "ecommerce.json"; // Archivo principal para categorías
    private static List<Categoria> categorias = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Gson gson = new Gson();

    public static List<Categoria> getCategorias() {
        return categorias;
    }

    // Método para cargar categorías desde el archivo JSON
    public static void cargarCategoriasYProductosDesdeJson() {
        try (BufferedReader br = new BufferedReader(new FileReader(JSON_FILE))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line);
            }
            if (jsonBuilder.length() > 0) {
                Type categoriaListType = new TypeToken<List<Categoria>>() {}.getType();
                categorias = gson.fromJson(jsonBuilder.toString(), categoriaListType);
                if (categorias == null) {
                    categorias = new ArrayList<>();
                }

                // Cargar productos de cada categoría
                for (Categoria categoria : categorias) {
                    GestionarProductos.cargarProductosPorCategoria(categoria);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las categorías y productos: " + e.getMessage());
        }
    }

    // Método para guardar categorías y productos en JSON
    public static void guardarCategoriasYProductosEnJson() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(JSON_FILE))) {
            String json = gson.toJson(categorias); // Guardar solo las categorías
            bw.write(json);
            System.out.println("Categorías guardadas correctamente.");

            // Guardar productos por cada categoría
            for (Categoria categoria : categorias) {
                GestionarProductos.guardarProductosPorCategoria(categoria);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las categorías y productos: " + e.getMessage());
        }
    }

    // Agregar una nueva categoría
    public static void agregarCategoria() {
        System.out.print("Ingrese el nombre de la nueva categoría: ");
        String nombre = scanner.nextLine();
        int id = categorias.isEmpty() ? 1 : categorias.get(categorias.size() - 1).getId() + 1; // ID único
        Categoria categoria = new Categoria(id, nombre);
        categorias.add(categoria);
        System.out.println("Categoría agregada exitosamente.");
        guardarCategoriasYProductosEnJson();
    }

    // Modificar una categoría existente
    public static void modificarCategoria() {
        mostrarCategorias();
        System.out.print("Ingrese el ID de la categoría a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                System.out.print("Ingrese el nuevo nombre de la categoría: ");
                String nuevoNombre = scanner.nextLine();
                categoria.setNombre(nuevoNombre);
                System.out.println("Categoría modificada exitosamente.");
                guardarCategoriasYProductosEnJson();
                return;
            }
        }
        System.out.println("Categoría no encontrada.");
    }

    // Eliminar una categoría
    public static void eliminarCategoria() {
        mostrarCategorias();
        System.out.print("Ingrese el ID de la categoría a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        categorias.removeIf(categoria -> categoria.getId() == id);
        System.out.println("Categoría eliminada exitosamente.");
        guardarCategoriasYProductosEnJson();
    }

    // Mostrar todas las categorías disponibles
    public static void mostrarCategorias() {
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías disponibles.");
        } else {
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i + 1) + ". " + categorias.get(i).getNombre());
            }
        }
    }

    // Método para buscar una categoría por su ID
    public static Categoria buscarCategoriaPorId(int categoriaId) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == categoriaId) {
                return categoria;
            }
        }
        return null; // Si no se encuentra la categoría
    }
}
