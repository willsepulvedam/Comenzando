

public class Producto {
    private static int contadorId = 1; // Variable estática para incrementar el ID
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria; // Relación con categoría

    public Producto(int id, String nombre, double precio, int stock, Categoria categoriaId) {
        this.id = contadorId++; // Asigna el valor actual y luego lo incrementa
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoriaId; // Asignación de la categoría al producto
    }
    public static void setContadorId(int maxId) {
        contadorId = maxId + 1; // Asegura que el próximo ID sea mayor que el ID más alto
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() { 
        return categoria; 
    }
    public void setCategoria(Categoria categoria) {
         this.categoria = categoria; 
    }

    @Override
    public String toString() {
        return "Producto{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", precio=" + precio +
               ", stock=" + stock +
               '}';
    }
}
