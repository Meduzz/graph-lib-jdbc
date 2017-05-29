create table graph (
    graph varchar(255),
    startNode varchar(255),
    relationType varchar(255),
    endNode varchar(255));

create unique index graph_unq on graph (graph, startNode, relationType, endNode);

create index graph_idx on graph (graph);