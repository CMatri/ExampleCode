import os
import threading
import geoip2.database
from PIL import Image, ImageDraw

def loop(imin, imax, last, reader, im, draw):
	for i in range(imin, imax):
		for j in range(0, 255):
			for k in range(0, 255):
				addr = str(k) + '.' + str(j) + '.' + str(i) + '.' + str(last)
				try:
					response = reader.city(addr)
					handleCoords(im, draw, response.location.longitude, response.location.latitude)
					print(addr + " : " + str(response.location.longitude) + " : " + str(response.location.latitude))
				except Exception:
					continue

def makeImage(width, height):
	im = Image.new('RGBA', (width, height), (0, 0, 0, 255)) # Create a blank image
	return im

def plotPoint(im, draw, x, y):
	final = [0, 0, 0, 0]
	base = (6, 255, 6, 255)
	col = im.getpixel((x, y))
	for i in range(len(base)):
		final[i] = base[i] + col[i]
	draw.line((x, y, x, y), tuple(final), 1)

def handleCoords(im, draw, x, y):
	x += 180
	y += 90
	y = 180 - y
	x *= 10
	y *= 10
	plotPoint(im, draw, x, y)

def ping(host):
	response = os.system("ping -c 1 -w2 " + host + " > /dev/null 2>&1")
	return response == 0

world = makeImage(360 * 10, 180 * 10)
draw = ImageDraw.Draw(world)
reader = geoip2.database.Reader('GeoLite2-City.mmdb')

loop(100, 150, 40, reader, world, draw)

world.save('world.png')