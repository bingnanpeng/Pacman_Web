from PIL import Image
import numpy as np
horizonal = Image.open('temp.png')
img = np.asarray(horizonal)
img.flags.writeable = True
for i in range(250,350):
	for j in range(600):
		img[i,j,2] = 255
image_new = Image.fromarray(np.uint8(img))
image_new.save('horizonalWall.png')

vertical = Image.open('temp.png')
img = np.asarray(vertical)
img.flags.writeable = True
for i in range(600):
	for j in range(250,350):
		img[i,j,2] = 255
image_new = Image.fromarray(np.uint8(img))
image_new.save('verticalWall.png')

def isWall(x,y, cx,cy):
	d = np.sqrt((x-cx)*(x-cx) + (y-cy)*(y-cy))
	if d >= 250 and d <= 350:
		return True
	return False
	
img = np.asarray(Image.open('temp.png'))
img.flags.writeable = True
for i in range(600):
	for j in range(600):
		if isWall(i,j,600,600):
			img[i,j,2] = 255
image_new = Image.fromarray(np.uint8(img))
image_new.save('upperLeftWall.png')

img = np.asarray(Image.open('temp.png'))
img.flags.writeable = True
for i in range(600):
	for j in range(600):
		if isWall(i,j,600,0):
			img[i,j,2] = 255
image_new = Image.fromarray(np.uint8(img))
image_new.save('upperRightWall.png')

img = np.asarray(Image.open('temp.png'))
img.flags.writeable = True
for i in range(600):
	for j in range(600):
		if isWall(i,j,0,600):
			img[i,j,2] = 255
image_new = Image.fromarray(np.uint8(img))
image_new.save('lowerleftWall.png')

img = np.asarray(Image.open('temp.png'))
img.flags.writeable = True
for i in range(600):
	for j in range(600):
		if isWall(i,j,0,0):
			img[i,j,2] = 255
image_new = Image.fromarray(np.uint8(img))
image_new.save('lowerRightWall.png')