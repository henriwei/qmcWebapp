--set foreign_key_checks=0;
--truncate table question_multi_choice;
--truncate table choice;
--truncate table tag;
--truncate table tag_question_association;
--set foreign_key_checks=1;

insert into question_multi_choice (question) values ('who will you save at first, your mother or your wife');
insert into question_multi_choice (question) values ('What''s the difference between datasource and connection');
insert into question_multi_choice (question) values ('Do you prefer save money then spend it or spend credits first');
insert into question_multi_choice (question) values ('What''s the difference between truncate and delete in sql');

insert into choice values (1, 1, 'mother', true, 0);
insert into choice values (2, 1, 'daughter', false, 0);

insert into tag values (1, 'moral');
insert into tag values (2, 'computer science');
insert into tag values (3, 'sql');

insert into tag_question_association values (1,1);
insert into tag_question_association values (1,3);
insert into tag_question_association values (2,2);
insert into tag_question_association values (3,3);
insert into tag_question_association values (3,2);