#!/usr/bin/env python3
"""Comprueba una instancia real por HTTP usando únicamente la biblioteca estándar."""
import argparse
import datetime as dt
import json
from pathlib import Path
import urllib.error
import urllib.parse
import urllib.request
from html.parser import HTMLParser


class TextoPorId(HTMLParser):
    def __init__(self):
        super().__init__()
        self.objetivo = None
        self.textos = {}

    def handle_starttag(self, tag, attrs):
        attrs = dict(attrs)
        if attrs.get('id', '').startswith('resultado-'):
            self.objetivo = attrs['id']
            self.textos[self.objetivo] = ''

    def handle_data(self, data):
        if self.objetivo:
            self.textos[self.objetivo] += data

    def handle_endtag(self, tag):
        self.objetivo = None


def verificar(base):
    resultados = []

    def registrar(caso, esperado, observado, cumple):
        resultados.append(dict(caso=caso, esperado=esperado, observado=observado, cumple=cumple))

    def pedir(ruta, datos=None):
        body = None if datos is None else urllib.parse.urlencode(datos).encode('utf-8')
        req = urllib.request.Request(base + ruta, data=body,
                                     headers={'User-Agent': 'TecnoSoporte-PC3-Verificador/1.0'})
        with urllib.request.urlopen(req, timeout=120) as respuesta:
            return respuesta.status, respuesta.read().decode('utf-8')

    for ruta in ('/cotizaciones/nueva', '/servicios', '/ayuda', '/health'):
        estado, texto = pedir(ruta)
        registrar('GET ' + ruta, 'HTTP 200', f'HTTP {estado}', estado == 200)
        if ruta == '/health':
            registrar('Salud de la aplicación', 'UP', json.loads(texto).get('status'),
                      json.loads(texto).get('status') == 'UP')

    minimos = [
        ('01', 'DIAGNOSTICO', '2', 'NUEVO', '80.00', '0.00', '80.00'),
        ('02', 'MANTENIMIENTO', '2', 'FRECUENTE', '140.00', '14.00', '126.00'),
        ('03', 'INSTALACION', '3', 'INSTITUCIONAL', '150.00', '22.50', '127.50'),
    ]
    for caso, servicio, cantidad, tipo, subtotal, descuento, total in minimos:
        entrada = dict(nombre='Cliente de prueba', servicio=servicio, cantidad=cantidad, tipoCliente=tipo)
        estado, html = pedir('/cotizaciones', entrada)
        parser = TextoPorId()
        parser.feed(html)
        observado = parser.textos
        cumple = (estado == 200 and observado.get('resultado-subtotal') == 'S/ ' + subtotal
                  and observado.get('resultado-descuento') == '− S/ ' + descuento
                  and observado.get('resultado-total') == 'S/ ' + total)
        registrar('Caso ' + caso, dict(entrada=entrada, subtotal=subtotal, descuento=descuento, total=total),
                  observado, cumple)

    invalidos = [('nombre', ''), ('nombre', '   '), ('cantidad', '0'), ('cantidad', '6'),
                 ('cantidad', '1.5'), ('cantidad', 'abc'), ('servicio', 'INVENTADO'),
                 ('tipoCliente', 'VIP')]
    for campo, valor in invalidos:
        entrada = dict(nombre='Cliente de prueba', servicio='DIAGNOSTICO', cantidad='1', tipoCliente='NUEVO')
        entrada[campo] = valor
        estado, html = pedir('/cotizaciones', entrada)
        cumple = estado == 200 and 'Revisa los datos de tu solicitud.' in html and 'id="resultado-total"' not in html
        registrar(f'Validación {campo}={valor!r}', 'Formulario con validación y sin total',
                  'Validación mostrada' if cumple else 'Respuesta inesperada', cumple)

    return resultados


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--url', default='http://localhost:8080')
    parser.add_argument('--salida', type=Path)
    args = parser.parse_args()
    base = args.url.rstrip('/')
    if urllib.parse.urlparse(base).scheme not in ('http', 'https'):
        parser.error('La URL debe comenzar con http:// o https://')
    try:
        resultados = verificar(base)
    except (urllib.error.URLError, TimeoutError, ValueError) as exc:
        raise SystemExit(f'No se pudo verificar la instancia {base}: {exc}') from exc
    informe = dict(url=base, fecha=dt.datetime.now(dt.timezone(dt.timedelta(hours=-5))).isoformat(),
                   total=len(resultados), correctos=sum(c['cumple'] for c in resultados), casos=resultados)
    for caso in resultados:
        print(('OK ' if caso['cumple'] else 'ERROR ') + caso['caso'])
    print(f"{informe['correctos']}/{informe['total']} comprobaciones correctas en {base}")
    if args.salida:
        args.salida.parent.mkdir(parents=True, exist_ok=True)
        args.salida.write_text(json.dumps(informe, ensure_ascii=False, indent=2), encoding='utf-8')
    return 0 if informe['correctos'] == informe['total'] else 1


if __name__ == '__main__':
    raise SystemExit(main())
