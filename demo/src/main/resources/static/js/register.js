// Esperar a que cargue el DOM
document.addEventListener('DOMContentLoaded', function() {
    console.log('🚀 JavaScript cargado correctamente');

    // Seleccionar elementos del DOM
    const slidePage = document.querySelector(".slide-page");
    const nextBtns = document.querySelectorAll(".next");
    const prevBtns = document.querySelectorAll(".prev");
    const progress = document.querySelectorAll(".step");
    const form = document.querySelector("form");
    const departamentoSelect = document.getElementById('departamento');
    const ciudadSelect = document.getElementById('ciudad');
    
    // Verificar que los elementos existen
    console.log('📄 Slide page encontrada:', slidePage);
    console.log('➡️ Botones Next:', nextBtns.length);
    console.log('⬅️ Botones Prev:', prevBtns.length);
    console.log('📊 Steps de progreso:', progress.length);
    
    let current = 0;

    // Función para actualizar la vista
    function actualizarVista(nuevoIndice) {
        console.log('🔄 Actualizando vista - Índice:', nuevoIndice);
        
        // Calcular y aplicar margen
        const margen = `-${nuevoIndice * 33.333}%`;
        console.log('📏 Margen aplicado:', margen);
        slidePage.style.marginLeft = margen;
        
        // Actualizar barra de progreso
        progress.forEach((step, i) => {
            if (i <= nuevoIndice) {
                step.classList.add("active");
            } else {
                step.classList.remove("active");
            }
        });
        
        current = nuevoIndice;
    }

    // Botones "Siguiente"
    nextBtns.forEach((btn, index) => {
        console.log(`✅ Botón Next ${index} registrado`);
        btn.addEventListener("click", function(event) {
            event.preventDefault();
            console.log(`▶️ Click en Siguiente - Index: ${index}, Current: ${current}`);
            
            if (current < 2) { // Máximo 2 (tercera página, índice 2)
                actualizarVista(current + 1);
            }
        });
    });

    // Botones "Atrás"
    prevBtns.forEach((btn, index) => {
        console.log(`✅ Botón Prev ${index} registrado`);
        btn.addEventListener("click", function(event) {
            event.preventDefault();
            console.log(`◀️ Click en Atrás - Index: ${index}, Current: ${current}`);
            
            if (current > 0) { // Mínimo 0 (primera página)
                actualizarVista(current - 1);
            }
        });
    });

    // Filtrar ciudades por departamento
    departamentoSelect.addEventListener('change', function() {
        const departamentoId = this.value;
        console.log('🏠 Departamento seleccionado:', departamentoId);

        // Ocultar todas las ciudades
        Array.from(ciudadSelect.options).forEach(option => {
            const dataDepartamento = option.getAttribute('data-departamento');
            if (!dataDepartamento) return; // Ignorar la opción por defecto

            if (dataDepartamento === departamentoId) {
                option.style.display = ''; // Mostrar
            } else {
                option.style.display = 'none'; // Ocultar
            }
        });

        // Resetear la selección de ciudad
        ciudadSelect.value = '';
    });

});