import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [productos, setProductos] = useState([]);
  const [nuevoProducto, setNuevoProducto] = useState({ nombre: '', descripcion: '', precio: 0, cantidad_en_stock: 0 });
  const [valorInventario, setValorInventario] = useState(0);
  const [valorCompra, setValorCompra] = useState(0);
  const [combinaciones, setCombinaciones] = useState([]);
  const [productoEditando, setProductoEditando] = useState(null);

  useEffect(() => {
    fetchProductos();
  }, []);

  const fetchProductos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/productos/todos');
      setProductos(response.data);
    } catch (error) {
      console.error('Error al obtener productos:', error);
    }
  };

  const handleCrearProducto = async () => {
    if (!nuevoProducto.nombre || !nuevoProducto.descripcion || !nuevoProducto.precio || !nuevoProducto.cantidad_en_stock) {
      alert('Por favor completa todos los campos.');
      return;
    }
    try {
      await axios.post('http://localhost:8080/api/productos/crear', nuevoProducto);
      setNuevoProducto({ nombre: '', descripcion: '', precio: 0, cantidad_en_stock: 0 });
      fetchProductos();
      alert('Producto creado exitosamente');
    } catch (error) {
      console.error('Error al crear producto:', error);
      alert('Error al crear producto. Por favor inténtalo de nuevo.');
    }
  };

  const handleEliminarProducto = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/productos/eliminar/${id}`);
      fetchProductos();
      alert('Producto eliminado exitosamente');
    } catch (error) {
      console.error('Error al eliminar producto:', error);
      alert('Error al eliminar producto. Por favor inténtalo de nuevo.');
    }
  };

  const handleActualizarProducto = async () => {
    if (!productoEditando) {
      alert('No se ha seleccionado ningún producto para editar.');
      return;
    }
  
    try {
      await axios.put(`http://localhost:8080/api/productos/actualizar/${productoEditando.id}`, nuevoProducto);
      setProductoEditando(null);
      setNuevoProducto({ nombre: '', descripcion: '', precio: 0, cantidad_en_stock: 0 });
      fetchProductos();
      alert('Producto actualizado exitosamente');
    } catch (error) {
      console.error('Error al actualizar producto:', error);
      alert('Error al actualizar producto. Por favor inténtalo de nuevo.');
    }
  };
  

  const handleCalcularValorInventario = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/productos/valor-total-inventario');
      setValorInventario(response.data);
    } catch (error) {
      console.error('Error al calcular valor del inventario:', error);
    }
  };

  const handleEditarProducto = (producto) => {
    setProductoEditando(producto);
    // Llenar el formulario con los datos del producto para editar
    setNuevoProducto({
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      cantidad_en_stock: producto.cantidad_en_stock
    });
  };

  return (
    <div className="container py-5">
      <h1 className="text-center mb-5">CRUD DE PRODUCTOS</h1>
      
      <div className="row">
        <div className="col">
          <h2>Listado de Productos</h2>
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {productos.map(producto => (
                <tr key={producto.id}>
                  <td>{producto.id}</td>
                  <td>{producto.nombre}</td>
                  <td>{producto.descripcion}</td>
                  <td>${producto.precio}</td>
                  <td>{producto.cantidad_en_stock}</td>
                  <td>
                    <button className="btn btn-info btn-sm mr-2" onClick={() => handleEditarProducto(producto)}>Editar</button>
                    <button className="btn btn-danger btn-sm" onClick={() => handleEliminarProducto(producto.id)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      <div className="row">
        <div className="col">
          <h2>{productoEditando ? 'Editar Producto' : 'Agregar Nuevo Producto'}</h2>
          <form>
            <div className="form-group">
            <label htmlFor="nombre">Nombre:</label>
              <input type="text" className="form-control mb-2" placeholder="Nombre" value={nuevoProducto.nombre} onChange={(e) => setNuevoProducto({ ...nuevoProducto, nombre: e.target.value })} />
            </div>
            <div className="form-group">
            <label htmlFor="descripcion">Descripción:</label>
              <input type="text" className="form-control mb-2" placeholder="Descripción" value={nuevoProducto.descripcion} onChange={(e) => setNuevoProducto({ ...nuevoProducto, descripcion: e.target.value })} />
            </div>
            <div className="form-group">
            <label htmlFor="precio">Precio:</label>
              <input type="number" className="form-control mb-2" placeholder="Precio" value={nuevoProducto.precio} onChange={(e) => setNuevoProducto({ ...nuevoProducto, precio: e.target.value })} />
            </div>
            <div className="form-group">
            <label htmlFor="stock">Stock:</label>
              <input type="number" className="form-control mb-2" placeholder="Stock" value={nuevoProducto.cantidad_en_stock} onChange={(e) => setNuevoProducto({ ...nuevoProducto, cantidad_en_stock: e.target.value })} />
            </div>
            {productoEditando ? (
              <button type="button" className="btn btn-success" onClick={handleActualizarProducto}>Actualizar Producto</button>
            ) : (
              <button type="button" className="btn btn-primary" onClick={handleCrearProducto}>Agregar Producto</button>
            )}
          </form>
        </div>
      </div>

      <div className="row mt-5">
        <div className="col">
          <h2>Valor Total del Inventario: ${valorInventario}</h2>
          <button type="button" className="btn btn-primary" onClick={handleCalcularValorInventario}>Calcular Valor Inventario</button>
        </div>
      </div>

    </div>
  );
}

export default App;
