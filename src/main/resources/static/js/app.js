// Navegation Menu
const btnMenu = document.querySelector('.btn-menu');
const barIconX = document.querySelector('.btn-menu i');
const menu = document.querySelector('.list-container');
const menuContent = document.querySelector('.menu');
let activador = true;

btnMenu.addEventListener('click', () => {
    menu.classList.toggle('active');
    barIconX.classList.toggle('fa-times');

    if (activador) {
        menu.style.left = '0%';
    } else {
        menu.style.left = '-100%';
    }

    activador = !activador;
});

// Add class "active" to selected menu item
document.querySelectorAll('.lists li a').forEach((element) => {
    element.addEventListener('click', (event) => {
        document.querySelectorAll('.lists li a').forEach(link => link.classList.remove('active'));
        event.target.classList.add('active');
    });
});

// Scroll Effect
let prevScrollPos = window.pageYOffset;
const goTop = document.querySelector('.go-top');

window.addEventListener('scroll', () => {
    let currentScrollPos = window.pageYOffset;
    requestAnimationFrame(() => {
        if (prevScrollPos > currentScrollPos) {
            menuContent.style.top = '0px';
        } else {
            menuContent.style.top = '-60px';
        }
        prevScrollPos = currentScrollPos;

        if (currentScrollPos <= 600) {
            menuContent.style.borderBottom = 'none';
            goTop.style.right = '-100px';
        } else {
            menuContent.style.borderBottom = '3px solid #ff2e63';
            goTop.style.right = '0px';
        }
    });
});

document.getElementById("ver-mas").addEventListener("click", async function () {
    try {
        const response = await fetch("http://localhost:8081/api/productos"); // Llamada al backend
        if (!response.ok) throw new Error("Error al obtener productos");

        const productos = await response.json(); // Convertimos la respuesta en JSON
        mostrarProductos(productos); // Llamamos a la función para mostrarlos
    } catch (error) {
        console.error("Error:", error);
    }
});

function mostrarProductos(productos) {
    const contenedor = document.getElementById("lista-productos");
    contenedor.innerHTML = ""; // Limpiar contenido antes de agregar nuevos

    productos.forEach(producto => {
        const div = document.createElement("div");
        div.classList.add("producto");
        div.innerHTML = `
            <h3>${producto.nombre}</h3>
            <p>Precio: $${producto.precio}</p>
            <img src="${producto.imagen}" alt="${producto.nombre}" width="150">
        `;
        contenedor.appendChild(div);
    });
}


// Go Top Click
goTop.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
});

// Scroll to Section Smoothly
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
        e.preventDefault();
        const targetElement = document.querySelector(this.getAttribute('href'));
        if (targetElement) {
            targetElement.scrollIntoView({ behavior: 'smooth' });
        }
    });
});

// Form Validation
document.getElementById("formulario").addEventListener("submit", function(event) {
    let userName = document.getElementById("user").value.trim();
    let userEmail = document.getElementById("email").value.trim();
    let userMessage = document.getElementById("message").value.trim();

    if (!userName || !userEmail || !userMessage) {
        alert("Por favor, completa todos los campos.");
        event.preventDefault();
        return;
    }

    // Validar formato de correo
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(userEmail)) {
        alert("Por favor, ingresa un correo electrónico válido.");
        event.preventDefault();
    }
});

//abajo
document.querySelector('#abajo').addEventListener('click', () => {
  window.scrollTo({
      top: 600,
      behavior: 'smooth'
  });
});

//Ver más
document.querySelector('#vermas').addEventListener('click', () => {
  window.scrollTo({
      top: 600,
      behavior: 'smooth'
  });
});
