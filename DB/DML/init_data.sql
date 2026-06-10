-- 超级管理员用户 (password: admin123, BCrypt加密)
INSERT INTO sys_user (id, account, password, nick_name, status) VALUES
(1, 'admin', '$2a$10$B7eOE32f1Eny6AWdY/BrMujLfKnjaXk.u6AQQC74tEYO.vwoSDr1.', '超级管理员', 1);
