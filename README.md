Marin Eduard-Constantin, 321CA
<br>
January 2022

<h1>Santa - Stage 1</h1>

<h2>Flow</h2>

-------------------------------------------------------------------------------
Sequentially, the input for every test is parsed and loaded into auxiliary
classes  that stores the data in a raw format, then the information is
processed and sent  into dedicated classes (from entity package).

After that, for every year of the simulation, a set of operations are applied
on the entities, with the goal of distributing the  available gifts to the
children on Santa's list.

In the end, the output of the simulation is stored in an array of JSON nodes,
every node representing the output generated after a year of simulation.
To achieve this, in every year, children on Santa's list have their data copied
into new child entities (using deep copy, so the next changes won't affect the
old data).

For every round of the simulation, the following actions happen:
- <b>the current year updates are loaded into classes</b> (and some additionally
  changes are made, such as removing the children > 18yo from Santa's list)
- <b>the budget is calculated for every child in Santa's list</b>
- <b>the gifts are distributed to children</b>, following the preferences
  of every child, without exceeding a child's assigned budget
- <b>a new node is added to the output array</b>, with the current data
  from every child which is in Santa's list



<h2>Design</h2>

-------------------------------------------------------------------------------

<h3>Command Pattern</h3>

To implement the operations that change the state of an object,
such as updating a Child/Santa with new gathered data ("yearly update")
or calculating the budget unit/child's budget, I used the Command Pattern,
wrapping the actions in Command type objects.<br>
The advantage of this approach is that if at some point, an update have to
be undone, the state of the objects can be rolled back easily, without changing
the structure of the code so much.
-------------------------------------------------------------------------------

<h3>Strategy Pattern</h3>
Because every age category implies a specific way to calculate the average
score of a child, I chose to create multiple <b>strategies</b>, one for every 
algorithm used to get the score.

For a future-proof implementation, the same logic is applied at gift
distribution, since there can be <b>multiple ways</b> to distribute the
gifts between children. Currently, only one method is  available (which
distributes the gifts to children sorted by their id-s),  because at this
stage of the project the order doesn't really matter, since the amount of
gifts owned by Santa is infinite, but in the future new distribution
strategies can be easily added.
-------------------------------------------------------------------------------

<h3>Factory Pattern</h3>
In order to generate a strategy object to calculate the average score
for a certain age category, the <b>factory pattern</b> has been chosen.
In this manner, depending on a child's age, the proper average
score calculation strategy is created.
-------------------------------------------------------------------------------

<h3>Singleton Pattern</h3>
-invoker, simulation, factory
In order to have an instance available from anywhere within the program
and to store all the executed commands on a common history, the <b>Invoker</b>
for the Command Pattern is implemented as a <b>singleton</b>.

Also, the factory which generates strategy objects to calculate the average
score and class used to run the simulation are Singletons.
------------------------------------------------------------