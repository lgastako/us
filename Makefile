all:
	@cat Makefile

t:
	lein spec -a

ts:
	lein cljsbuild auto test
