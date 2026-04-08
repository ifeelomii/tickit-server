-- Seed Project
INSERT INTO projects (id, public_id, name)
VALUES (1, '11111111-1111-1111-1111-111111111111', 'Default Project');

-- If DB doesn't support gen_random_uuid() we can hardcode the id as done
-- VALUES (1, gen_random_uuid(), 'Default Project');

-- Seed Workflow
INSERT INTO workflows (id, public_id, project_id, name, active)
VALUES
(1, '22222222-2222-2222-2222-222222222222', 1, 'Default Workflow', true);


-- Seed Statuses
INSERT INTO statuses (id, public_id, name, category, workflow_id)
VALUES
(1, '33333333-3333-3333-3333-333333333331', 'Open', 'TODO', 1),
(2, '33333333-3333-3333-3333-333333333332', 'In Progress', 'IN_PROGRESS', 1),
(3, '33333333-3333-3333-3333-333333333333', 'Review', 'IN_PROGRESS', 1),
(4, '33333333-3333-3333-3333-333333333334', 'Done', 'DONE', 1),
(5, '33333333-3333-3333-3333-333333333335', 'Blocked', 'IN_PROGRESS', 1);


-- Seed Status Transitions
INSERT INTO status_transitions (id, public_id, workflow_id, from_status_id, to_status_id)
VALUES
(1, '44444444-4444-4444-4444-444444444441', 1, 1, 2), -- Open → In Progress
(2, '44444444-4444-4444-4444-444444444442', 1, 2, 3), -- In Progress → Review
(3, '44444444-4444-4444-4444-444444444443', 1, 3, 4), -- Review → Done
(4, '44444444-4444-4444-4444-444444444444', 1, 3, 2), -- Review → In Progress (rework)
(5, '44444444-4444-4444-4444-444444444445', 1, 2, 5), -- In Progress → Blocked
(6, '44444444-4444-4444-4444-444444444446', 1, 5, 2); -- Blocked → In Progress