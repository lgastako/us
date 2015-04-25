all:
	@cat Makefile

t:
	lein test

ts:
	lein cljsbuild auto test
