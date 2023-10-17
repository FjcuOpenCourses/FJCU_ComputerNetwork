# Homework #3 
## Simulation of a Simple Queueing System

## Due on 2022-01-19 

## Objective:  
This laboratory is to familiarize the students with the use of simulation to study the performance of a simple queueing system under various conditions.

## Background:
Suppose that there is a router with one incoming link.  Assume that on the link the packets arrive following a Poisson process, i.e., the interarrival time between two consecutive packets on each link is exponentially distributed with probability density function equal to a(t) = e-t , t >= 0 (The average interarrival time is equal to 1/, and  [packets/sec] is called the arrival rate). Packets arriving at the router enter a queue and are served by the CPU sequentially.  The queue is of infinite capacity.  Assume that the service time a packet requires is also exponentially distributed with probability density function b(t) = e-t , t >= 0 (The average service time is equal to 1/, and  [packets/sec] is called the service rate). A packet exits the system after it is processed by the CPU.
