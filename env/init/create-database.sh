#!/bin/sh

createuser todo-user -P
createdb -E utf8 -O todo-user boot-todo
