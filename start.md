docker run --name=prometheus -d -p 9090:9090 -v /Users/banuprakash/Desktop/"Java Workspaces"/ADOBE/FEB23/JAVA_17_SPRING/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml


docker cp  /Users/banuprakash/Desktop/"Java Workspaces"/ADOBE/FEB23/JAVA_17_SPRING/rules.yml prometheus:/etc/prometheus/rules.yml


docker run -d --name=grafana -p 3000:3000 grafana/grafana

Grafana:
http://host.docker.internal:9090