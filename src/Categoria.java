import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private static int contadorId = 1; // Contador estático para generar IDs únicos
    private int id;
    private String nombre;
    private List<Producto> productos;

    public Categoria(String nombre) {
        this.id = contadorId++; // Asigna un ID único
        this.nombre = nombre;
        this.productos = new ArrayList<>(); // Inicializa la lista de productos
    }

    // Constructor para cargar desde JSON, con ID especificado
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }
    public static void setContadorId(int maxId) {
        contadorId = maxId + 1; // Asegura que el próximo ID sea mayor que el ID más alto
    }

    // Métodos getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
         this.productos = productos; 
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    @Override
    public String toString() {
        return "Categoria{id=" + id + ", nombre='" + nombre + "', productos=" + productos.size() + " productos}";
    }
}


