#curl -s http://localhost:9000/messages -F from='MiStuart <mistuart@corelogic.com>' -F to=mike@stuart.org -F to=mwstuart@live.com -F subject='Hello' -F text='Testing mail'
#curl -v http://localhost:9000/messages -F from=mistuart@corelogic.com -F to=mike@stuart.org -F to=mwstuart@live.com -F subject='Hello' -F text='Testing mail'

## good
#curl -v http://localhost:9000/messages -F from=mistuart@corelogic.com -F to=mistuart@corelogic.com -F subject='Hello' -F text='Testing mail'
# From Alias
#curl -v http://localhost:9000/messages -F from='Michael W. Stuart <mistuart@corelogic.com>' -F to=mistuart@corelogic.com -F subject='Hello' -F text='Testing mail'
# requires server to be run on a valid smtp relay host (wap05/06/07, etc.)
#curl -v http://wap05:9000/messages -F from=mistuart@corelogic.com -F to=mike@stuart.org -F subject='Hello' -F text='Testing mail'
#curl -v http://dmap02:9000/messages -F from=mistuart@corelogic.com -F to=mike@stuart.org -F to=mwstuart@live.com -F subject='Hello' -F text='Testing 2 mail'
curl -v http://wap05:9000/messages -F from=mistuart@corelogic.com -F to=mike@stuart.org -F to=mwstuart@live.com -F bcc=michaelwaynestuart@gmail.com -F subject='Hello' -F text='Testing 2 mail'
