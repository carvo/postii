CREATE (u:User {
  name:      'Ricardo',
  username:  'Ricardo',
  createdAt: localDateTime()
});

CREATE (u:User {
  name:      'Neymar',
  username:  'Neymar',
  createdAt: localDateTime()
});

CREATE (u:User {
  name:      'Lula',
  username:  'Lula',
  createdAt: localDateTime()
});

CREATE (u:User {
  name:      'Ciro',
  username:  'Ciro',
  createdAt: localDateTime()
});

CREATE (u:User {
  name:      'Bolsonaro',
  username:  'Bolsonaro',
  createdAt: localDateTime()
});


MATCH (ciro:User {username: 'Ciro'}),
      (lula:User {username: 'Lula'})
CREATE (lula)-[:FOLLOWS]->(ciro),
       (ciro)-[:FOLLOWS]->(lula);

MATCH (ricardo:User {username: 'Ricardo'}),
      (lula:User {username: 'Lula'}),
      (ciro:User {username: 'Ciro'}),
      (ney:User {username: 'Neymar'})
CREATE (lula)-[:FOLLOWS]->(ricardo),
       (ciro)-[:FOLLOWS]->(ricardo),
       (ney)-[:FOLLOWS]->(ricardo),
       (ney)-[:FOLLOWS]->(lula),
       (ney)-[:FOLLOWS]->(ciro);