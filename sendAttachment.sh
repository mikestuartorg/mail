curl -v http://localhost:9000/messages -F name='Mike Stuart' -F to=mistuart@corelogic.com -F attachment=@file.pdf
# test 5MB upload limit
#curl -v http://localhost:9000/messages -F name='Mike Stuart' -F to=mistuart@corelogic.com -F attachment=@/Users/mike/Documents/WebSphere_NET_interop.pdf
