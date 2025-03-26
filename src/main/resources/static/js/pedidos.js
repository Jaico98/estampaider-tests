document.addEventListener("DOMContentLoaded", function () {
    const pedidoForm = document.getElementById("pedidoForm");
    const listaPedidos = document.getElementById("listaPedidos");

    // Función para cargar pedidos
    function cargarPedidos() {
        fetch("/pedidos")
            .then(response => response.json())
            .then(data => {
                listaPedidos.innerHTML = "";
                data.forEach(pedido => {
                    const li = document.createElement("li");
                    li.textContent = `${pedido.cliente} pidió ${pedido.cantidad} de ${pedido.producto}`;
                    listaPedidos.appendChild(li);
                });
            });
    }

    // Enviar pedido
    pedidoForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const producto = document.getElementById("producto").value;
        const cantidad = document.getElementById("cantidad").value;
        const cliente = document.getElementById("cliente").value;

        fetch("/pedidos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ producto, cantidad, cliente })
        })
        .then(response => response.json())
        .then(() => {
            pedidoForm.reset();
            cargarPedidos();
        });
    });

    cargarPedidos();
});
