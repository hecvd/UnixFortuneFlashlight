from sys import argv
from sys import maxint
import json

def build_fortune_data_file(fpath):
	fdatpath = fpath + ".dat"
	print 'Updating "%s" from "%s"' % (fdatpath, fpath)
	
	data = []
	shortest = maxint
	longest = 0
	
	with open(fpath) as pfortune:
		pos = 0
		lenght = 0
		shortest = maxint
		longest = 0
		fortune = []
		for line in pfortune:
			if line == '%\n':
				lenght = len("".join(fortune))
				data += [(pos, lenght)]
				pos += lenght + len(line)
				shortest = min(shortest, lenght)
				longest = max(longest, lenght)
				fortune = []
			else:
				fortune.append(line)
				
	data_dict = {'shortest':shortest, 'longest':longest, 'fortune_count':len(data), 'fortunes':data}
	with open(fdatpath, 'w') as outfdat:
		json.dump(data_dict,outfdat)
	

for file in argv:
	if not file.endswith('.dat'):
		build_fortune_data_file(file)

