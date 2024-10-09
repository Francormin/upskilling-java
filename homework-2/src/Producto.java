import interfaces.Filtrable;

public class Producto implements Comparable<Producto>, Filtrable<Producto> {
    private String nombre;
    private double precio;
    private String codigo;
    private boolean tieneStock;

    public Producto() {};

    public Producto(String nombre, double precio, String codigo) {
        this.nombre = nombre;
        this.precio = precio;
        this.codigo = codigo;
    }

    public Producto(String nombre, double precio, String codigo, boolean tieneStock) {
        this(nombre, precio, codigo);
        this.tieneStock = tieneStock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nuevoNombre) {
        this.nombre = nuevoNombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double nuevoPrecio) {
        this.precio = nuevoPrecio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String nuevoCodigo) {
        this.codigo = nuevoCodigo;
    }

    public boolean isTieneStock() {
        return tieneStock;
    }

    public void setTieneStock(boolean tieneStock) {
        this.tieneStock = tieneStock;
    }

    @Override
    public int compareTo(Producto otroProducto) {
        if (this.precio > otroProducto.precio) {
            return 1;
        } else if (this.precio < otroProducto.precio) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean cumpleFiltro(Producto producto) {
        if (producto.isTieneStock())
            return true;
        else return false;
    }

    @Override
    public String toString() {
        return "\n" + "{ " +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", codigo='" + codigo + '\'' +
                ", tieneStock=" + tieneStock +
                " }";
    }
}