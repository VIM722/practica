/* La vista previa es orientativa. Spring calcula y valida el resultado final. */
(() => {
  'use strict';
  document.querySelector('[data-print]')?.addEventListener('click', () => window.print());
  const form = document.querySelector('#cotizacion-form');
  if (!form) return;
  const quantity = form.querySelector('#cantidad');
  const money = value => `S/ ${value.toFixed(2)}`;
  const put = (id, value) => { document.getElementById(id).textContent = value; };
  function update() {
    const service = form.querySelector('input[name="servicio"]:checked');
    const client = form.querySelector('input[name="tipoCliente"]:checked');
    const count = Number(quantity.value);
    const validCount = quantity.value !== '' && Number.isInteger(count) && count >= 1 && count <= 5;
    const rate = Number(client?.dataset.discount ?? 0);
    put('preview-service', service?.dataset.name ?? 'Selecciona un servicio');
    put('preview-quantity', validCount ? `${count} ${count === 1 ? 'unidad' : 'unidades'}` : 'Cantidad de 1 a 5');
    put('preview-rate', `${rate}%`);
    put('preview-unit', service ? money(Number(service.dataset.price)) : 'S/ —');
    const ready = service && client && validCount;
    const subtotal = ready ? Math.round(Number(service.dataset.price) * 100) * count : 0;
    const discount = Math.round(subtotal * rate / 100);
    put('preview-subtotal', ready ? money(subtotal / 100) : 'S/ —');
    put('preview-discount', ready ? `− ${money(discount / 100)}` : 'S/ —');
    put('preview-total', ready ? money((subtotal - discount) / 100) : 'S/ —');
    form.querySelector('[data-step="-1"]').disabled = validCount && count <= 1;
    form.querySelector('[data-step="1"]').disabled = validCount && count >= 5;
  }
  form.querySelectorAll('[data-step]').forEach(button => button.addEventListener('click', () => {
    const current = Number(quantity.value);
    const base = Number.isInteger(current) ? current : 1;
    quantity.value = Math.max(1, Math.min(5, base + Number(button.dataset.step)));
    quantity.dispatchEvent(new Event('input', { bubbles: true }));
  }));
  form.addEventListener('input', update);
  form.addEventListener('change', update);
  update();
  document.querySelector('.error-summary')?.focus();
})();
