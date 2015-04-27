DEV_PORT=3000

all:
	@cat Makefile

bd:
	boot dev

bs:
	boot serve

dl:
	divshot login

dp:
	divshot push

o:
	open http://localhost:$(DEV_PORT)/
