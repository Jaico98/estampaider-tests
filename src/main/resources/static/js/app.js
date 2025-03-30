// Navegation Menu
const btnMenu = document.querySelector('.btn-menu');
const barIconX = document.querySelector('.btn-menu i');
const menu = document.querySelector('.list-container');
const menuContent = document.querySelector('.menu');

btnMenu?.addEventListener('click', () => {
    menu?.classList.toggle('active');
    barIconX?.classList.toggle('fa-times');
});

// Agregar clase "active" al menú seleccionado
document.querySelectorAll('.lists li a').forEach(element => {
    element.addEventListener('click', event => {
        document.querySelector('.lists li a.active')?.classList.remove('active');
        event.target.classList.add('active');
    });
});

// Scroll Effect con debounce
let prevScrollPos = window.pageYOffset;
const goTop = document.querySelector('.go-top');

function debounce(func, wait = 50) {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

window.addEventListener('scroll', debounce(() => {
    let currentScrollPos = window.pageYOffset;

    if (menuContent) {
        menuContent.style.top = prevScrollPos > currentScrollPos ? '0px' : '-60px';
        menuContent.style.borderBottom = currentScrollPos <= 600 ? 'none' : '3px solid #ff2e63';
        prevScrollPos = currentScrollPos;
    }

    goTop?.style.setProperty('right', currentScrollPos <= 600 ? '-100px' : '0px');
}, 100));

// Obtener productos al hacer clic en "Ver más"
const btnVerMas = document.getElementById("ver-mas");

btnVerMas?.addEventListener("click", async function () {
    try {
        const response = await fetch("http://localhost:8081/api/productos");
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

        const productos = await response.json();
        mostrarProductos(productos);
    } catch (error) {
        console.error("Error obteniendo productos:", error.message);
        alert("Hubo un problema al cargar los productos. Inténtalo más tarde.");
    }
});

function mostrarProductos(productos) {
    const contenedor = document.getElementById("lista-productos");
    if (!contenedor) return;

    contenedor.innerHTML = "";
    productos.forEach(({ nombre = "Sin nombre", precio = "No disponible", imagen }) => {
        const div = document.createElement("div");
        div.classList.add("producto");

        div.innerHTML = `
            <h3>${nombre}</h3>
            <p>Precio: $${precio}</p>
            <img src="${imagen || 'placeholder.jpg'}" alt="${nombre}" width="150">
        `;
        contenedor.appendChild(div);
    });
}

// Botón Go Top
goTop?.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
});

// Scroll a secciones (Optimizado con delegación de eventos)
document.body.addEventListener("click", function(e) {
    if (e.target.tagName === "A" && e.target.getAttribute("href")?.startsWith("#")) {
        e.preventDefault();
        const targetElement = document.querySelector(e.target.getAttribute("href"));
        targetElement?.scrollIntoView({ behavior: "smooth" });
    }
});

// Validación de formulario con mensajes de error
const formulario = document.getElementById("formulario");

formulario?.addEventListener("submit", function(event) {
    let errores = [];
    const user = document.getElementById("user");
    const email = document.getElementById("email");
    const message = document.getElementById("message");

    if (!user.value.trim()) errores.push("El usuario es obligatorio.");
    if (!email.value.trim()) errores.push("El correo es obligatorio.");
    if (!message.value.trim()) errores.push("El mensaje no puede estar vacío.");

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email.value.trim())) errores.push("El correo electrónico no es válido.");

    if (errores.length > 0) {
        event.preventDefault();
        document.getElementById("error-msg").innerText = errores.join("\n");
    }
});

// Scroll hacia abajo (botón "abajo" y "ver más")
document.addEventListener("click", function(event) {
    if (event.target.id === "abajo" || event.target.id === "ver-mas") {
        window.scrollTo({
            top: 600,
            behavior: "smooth"
        });
    }
});
