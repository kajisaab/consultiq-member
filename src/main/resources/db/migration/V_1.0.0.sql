create table user_details
(
    id               text not null
        primary key,
    full_name        text not null,
    email            text not null
        unique,
    employee_id      text not null,
    user_type        text not null,
    deleted          boolean default false,
    blocked          boolean default false,
    active           boolean default false,
    roles            jsonb,
    branch           jsonb,
    created_by       jsonb,
    created_on       timestamp,
    last_modified_by jsonb,
    last_modified_on timestamp,
    user_image       text,
    user_position    text,
    selected_theme   text
);

create table roles
(
    id               text      not null
        primary key,
    title            text,
    permissions      jsonb,
    created_on       timestamp not null,
    created_by       jsonb     not null,
    last_modified_on timestamp,
    last_modified_by jsonb,
    deleted          boolean   not null,
    status           text,
    active           boolean default true
);

create table user_credential
(
    id                 text not null
        primary key,
    user_id            text,
    max_login_attempts integer default 5,
    login_attempts     integer default 0,
    password           text not null,
    generated_salt     text,
    password_history   jsonb,
    created_by         jsonb,
    created_on         timestamp,
    last_modified_by   jsonb,
    last_modified_on   timestamp,
    deleted            boolean default false
);

create table module_permission_detail
(
    id               text not null
        primary key,
    label            text,
    value            text,
    type             text,
    parent_id        text,
    created_on       timestamp,
    last_modified_on timestamp,
    position         integer,
    deleted          boolean default false,
    created_by       jsonb   default '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}'::jsonb,
    last_modified_by jsonb
);



INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('cd6b0a5f-5c57-4e3a-b7c5-c876a3f3303e', 'Education Firm', 'educationFirm', 'MODULE', null, '2024-12-15 04:11:51.115683', null, 3, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('ff972ed6-cf59-415a-b52d-1b57340dbabb', 'Eduction Firm List', 'educationFirm:list', 'PERMISSION', 'cd6b0a5f-5c57-4e3a-b7c5-c876a3f3303e', '2024-12-15 04:14:38.179590', null, 3, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('5d06d9c7-6bbb-4d4c-b388-b4a31083badf', 'Add Education Firm', 'educationFirm:add', 'PERMISSION', 'cd6b0a5f-5c57-4e3a-b7c5-c876a3f3303e', '2024-12-15 04:14:38.191944', null, 3, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('e5c4c004-3347-4d80-8b76-38180662af5e', 'Update Education Firm', 'educationFirm:update', 'PERMISSION', 'cd6b0a5f-5c57-4e3a-b7c5-c876a3f3303e', '2024-12-15 04:14:38.200930', null, 3, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('5d6215b9-7cd7-496d-9b5a-77d36f587731', 'Delete Education Firm', 'educationFirm:delete', 'PERMISSION', 'cd6b0a5f-5c57-4e3a-b7c5-c876a3f3303e', '2024-12-15 04:14:38.209897', null, 3, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('f94da6b2-43ff-4e10-bbf4-f3dce5d4e504', 'Education Firm Summary', 'educationFirm:summary', 'PERMISSION', 'cd6b0a5f-5c57-4e3a-b7c5-c876a3f3303e', '2024-12-15 04:14:38.217723', null, 3, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('8274125a-12f0-48f3-b5e5-bfb23d6b744a', 'Identity & Access Management', 'iam', 'MODULE', null, '2024-12-15 08:02:03.349777', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('45e238ec-96d8-41c1-992b-d4d924f4d897', 'Roles', 'iam:roles', 'GROUP', '8274125a-12f0-48f3-b5e5-bfb23d6b744a', '2024-12-15 08:04:18.307175', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('a8712dcd-6ae8-4aa2-aa99-82da275b1034', 'Role List', 'iam:roles:list', 'PERMISSION', '45e238ec-96d8-41c1-992b-d4d924f4d897', '2024-12-15 08:04:58.180996', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('b46df9ce-4ba3-4f2e-9b7d-0c89f8594ef6', 'Role Summary', 'iam:roles:summary', 'PERMISSION', '45e238ec-96d8-41c1-992b-d4d924f4d897', '2024-12-15 08:05:30.618165', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('1daf61e9-9fdb-4d5d-b9af-e11489d7edff', 'Update Role', 'iam:roles:update', 'PERMISSION', '45e238ec-96d8-41c1-992b-d4d924f4d897', '2024-12-15 08:05:41.586863', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('78037bd8-a84a-46e3-a7e4-0a47924341c3', 'Add Role', 'iam:roles:add', 'PERMISSION', '45e238ec-96d8-41c1-992b-d4d924f4d897', '2024-12-15 08:05:54.864697', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('12f61428-395d-4bc7-80eb-7a12e490407a', 'Users', 'iam:users', 'GROUP', '8274125a-12f0-48f3-b5e5-bfb23d6b744a', '2024-12-15 08:11:55.696318', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('e8098348-5b3f-43be-8869-73df5fb0ea93', 'Users List', 'iam:users:list', 'PERMISSION', '12f61428-395d-4bc7-80eb-7a12e490407a', '2024-12-15 08:15:51.332750', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('002cde69-d323-4ad1-8fd2-1f6356c736e2', 'Dashboard', 'dashboard', 'MODULE', null, '2024-12-15 03:31:59.254072', null, 1, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('45f715d6-6421-493d-8085-419508294b29', 'Client & Group', 'clientAndGroup', 'MODULE', null, '2024-12-15 03:31:59.254072', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('fe4c1113-6591-44b9-902c-d0ab1e1a7e51', 'Client', 'client', 'GROUP', '45f715d6-6421-493d-8085-419508294b29', '2024-12-15 04:04:17.537382', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('c858e46d-52e9-4b5f-a786-9f5a0b4a004b', 'GROUP', 'group', 'GROUP', '45f715d6-6421-493d-8085-419508294b29', '2024-12-15 04:04:19.302755', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('d79117d9-305f-437b-b45b-1906ecf7f942', 'Client List', 'client:clientList', 'PERMISSION', 'fe4c1113-6591-44b9-902c-d0ab1e1a7e51', '2024-12-15 04:07:57.130874', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('a8e08a43-8599-4389-9e24-951a15e4c668', 'Client Summary', 'client:summary', 'PERMISSION', 'fe4c1113-6591-44b9-902c-d0ab1e1a7e51', '2024-12-15 04:07:57.151398', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('a885f228-13d7-48ad-878b-a7bf365c3f44', 'Update Client', 'client:updateClient', 'PERMISSION', 'fe4c1113-6591-44b9-902c-d0ab1e1a7e51', '2024-12-15 04:07:57.163034', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('d014e700-02e9-4d41-bd15-76a24cdb4ee8', 'Add Client', 'client:addClient', 'PERMISSION', 'fe4c1113-6591-44b9-902c-d0ab1e1a7e51', '2024-12-15 04:07:57.170490', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('d80379ba-1dee-4084-b1f2-6feac905332c', 'Import Client Details', 'client:importClient', 'PERMISSION', 'fe4c1113-6591-44b9-902c-d0ab1e1a7e51', '2024-12-15 04:07:57.177670', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('f8e3973c-c823-4dde-b33f-5127e432c074', 'Export Client Details', 'client:exportClientDetails', 'PERMISSION', 'fe4c1113-6591-44b9-902c-d0ab1e1a7e51', '2024-12-15 04:07:57.187815', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('bde1d7f4-0311-4743-b572-4d1d2341d329', 'Delete Client', 'client:deleteClient', 'PERMISSION', 'fe4c1113-6591-44b9-902c-d0ab1e1a7e51', '2024-12-15 04:07:57.194605', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('cd09a4c3-dbe7-415e-b49f-51ad04321bbb', 'Group List', 'group:list', 'PERMISSION', 'c858e46d-52e9-4b5f-a786-9f5a0b4a004b', '2024-12-15 04:10:21.767985', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('bf06e18b-8e7c-417b-a05c-4919d9e8085a', 'Add Group', 'group:addGroup', 'PERMISSION', 'c858e46d-52e9-4b5f-a786-9f5a0b4a004b', '2024-12-15 04:10:21.780969', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('3d1d6800-df12-42df-9974-9d0cb4a534da', 'Delete Group', 'group:deleteGroup', 'PERMISSION', 'c858e46d-52e9-4b5f-a786-9f5a0b4a004b', '2024-12-15 04:10:21.789514', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('b3f10c05-a552-42bc-ac6a-3cb3f82bfee0', 'Edit Group', 'group:editGroup', 'PERMISSION', 'c858e46d-52e9-4b5f-a786-9f5a0b4a004b', '2024-12-15 04:10:21.797125', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('c31406fb-9c5b-4c91-b448-fc8ffc63ce70', 'Delete Group', 'group:deleteGroup', 'PERMISSION', 'c858e46d-52e9-4b5f-a786-9f5a0b4a004b', '2024-12-15 04:10:21.805590', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('4d81c2a3-977c-49b3-893c-6213c5caa9b4', 'Group Summary List', 'group:groupSummaryList', 'PERMISSION', 'c858e46d-52e9-4b5f-a786-9f5a0b4a004b', '2024-12-15 04:10:21.817558', null, 2, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('fb954b2a-e545-4023-a338-cf496c7b98de', 'Add User', 'iam:users:add', 'PERMISSION', '12f61428-395d-4bc7-80eb-7a12e490407a', '2024-12-15 08:16:02.609518', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('483f1ea3-cb93-436c-ac92-b7f53b7b3c34', 'Edit User', 'iam:users:edit', 'PERMISSION', '12f61428-395d-4bc7-80eb-7a12e490407a', '2024-12-15 08:16:18.970134', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('8ad18ec0-f977-484a-8ced-75d9118c995f', 'User Summary', 'iam:users:summary', 'PERMISSION', '12f61428-395d-4bc7-80eb-7a12e490407a', '2024-12-15 08:17:30.050211', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);
INSERT INTO consultancy_001.module_permission_detail (id, label, value, type, parent_id, created_on, last_modified_on, position, deleted, created_by, last_modified_by) VALUES ('b1c8ce7e-c467-4a43-a46d-9af65b33b0c0', 'Delete User', 'iam:users:delete', 'PERMISSION', '12f61428-395d-4bc7-80eb-7a12e490407a', '2024-12-15 08:17:40.703364', null, 4, false, '{"label": "system@consultIq.com", "value": "969e4527-cc4b-4570-9833-770810f9ff02"}', null);


INSERT INTO consultancy_001.roles (id, title, permissions, created_on, created_by, last_modified_on, last_modified_by, deleted, status, active) VALUES ('b6a35294-5bb4-4966-a19f-ac7c945982f0', 'SUPER USER', '["educationFirm", "educationFirm:list", "educationFirm:add", "educationFirm:update", "educationFirm:delete", "educationFirm:summary", "iam", "iam:roles", "iam:roles:list", "iam:roles:summary", "iam:roles:update", "iam:roles:add", "iam:users", "iam:users:list", "dashboard", "clientAndGroup", "client", "group", "client:clientList", "client:summary", "client:updateClient", "client:addClient", "client:importClient", "client:exportClientDetails", "client:deleteClient", "group:list", "group:addGroup", "group:deleteGroup", "group:editGroup", "group:deleteGroup", "group:groupSummaryList", "iam:users:add", "iam:users:edit", "iam:users:summary", "iam:users:delete"]', '2024-11-14 18:55:24.914112', '{"label": "SYSTEM", "value": "SYSTEM"}', null, null, false, 'ACTIVE', true);
INSERT INTO consultancy_001.user_credential (id, user_id, max_login_attempts, login_attempts, password, generated_salt, password_history, created_by, created_on, last_modified_by, last_modified_on, deleted) VALUES ('46cfe25e-f0fb-4161-bbcf-751d4c989d45', '5dfdaf97-983e-4167-9deb-573e2c56d8d4', 5, 0, '095925113c2be104145fc61232e7cc66117616fa9365e2d10f49f9966b23f81f', 'a46b3073-dc91-4948-952f-4c64c992bf10', '[]', '{"label": "SYSTEM", "value": "SYSTEM"}', '2024-12-08 20:45:39.295956', null, null, false);
INSERT INTO consultancy_001.user_details (id, full_name, email, employee_id, user_type, deleted, blocked, active, roles, branch, created_by, created_on, last_modified_by, last_modified_on, user_image, user_position, selected_theme) VALUES ('5dfdaf97-983e-4167-9deb-573e2c56d8d4', 'string', 'user@example.com', '0001', 'ADMIN', false, false, true, '[{"label": "SUPER USER", "value": "b6a35294-5bb4-4966-a19f-ac7c945982f0"}]', '{"label": "HO", "value": "HO"}', '{"label": "SYSTEM", "value": "SYSTEM"}', '2024-12-08 20:45:39.295956', null, null, null, 'Managing Director', 'dark');
