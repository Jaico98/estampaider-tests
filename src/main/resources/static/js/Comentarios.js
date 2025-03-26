document.addEventListener("DOMContentLoaded", function () {
    const comentarioForm = document.getElementById("comentarioForm");
    const listaComentarios = document.getElementById("listaComentarios");

    function cargarComentarios() {
        fetch("/comentarios")
            .then(response => response.json())
            .then(data => {
                listaComentarios.innerHTML = "";
                data.forEach(comentario => {
                    const li = document.createElement("li");
                    li.textContent = `${comentario.cliente} comentó sobre ${comentario.producto}: "${comentario.comentario}"`;
                    listaComentarios.appendChild(li);
                });
            });
    }

    comentarioForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const producto = document.getElementById("producto").value;
        const cliente = document.getElementById("cliente").value;
        const comentario = document.getElementById("comentario").value;

        fetch("/comentarios", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ producto, cliente, comentario })
        })
        .then(response => response.json())
        .then(() => {
            comentarioForm.reset();
            cargarComentarios();
        });
    });

    cargarComentarios();
});
