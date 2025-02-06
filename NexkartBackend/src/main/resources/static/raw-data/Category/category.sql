INSERT INTO categories (id, alias, all_parent_ids, enabled, image, name, parent_id) VALUES
                                                                                        -- Main Categories
                                                                                        (1, 'mobile-phones', NULL, b'1', 'mobile phones.jpeg', 'Mobile Phones & Accessories', NULL),
                                                                                        (2, 'laptops-computers', NULL, b'1', 'Electronics.jpeg', 'Laptops & Computers', NULL),
                                                                                        (3, 'cameras-photography', NULL, b'1', 'cameras.jpeg', 'Cameras & Photography', NULL),
                                                                                        (4, 'audio-home-theater', NULL, b'1', 'home theater.jpg', 'Audio & Home Theater', NULL),
                                                                                        (5, 'wearable-tech', NULL, b'1', 'waerable tech.jpeg', 'Wearable Technology & Smart Devices', NULL),

                                                                                        -- Subcategories for Mobile Phones & Accessories (parent_id = 1)
                                                                                        (6, 'smartphones', '-1-', b'1', 'mobile phones.jpeg', 'Smartphones', 1),
                                                                                        (7, 'feature-phones', '-1-', b'1', 'featured phones.jpg', 'Feature Phones', 1),
                                                                                        (8, 'mobile-accessories', '-1-', b'1', 'screen protector.jpeg', 'Mobile Accessories', 1),

                                                                                        -- Subcategories for Laptops & Computers (parent_id = 2)
                                                                                        (9, 'laptops', '-2-', b'1', 'Electronics.jpeg', 'Laptops', 2),
                                                                                        (10, 'desktops', '-2-', b'1', 'dekstops.jpeg', 'Desktops', 2),
                                                                                        (11, 'computer-accessories', '-2-', b'1', 'computer accessories.jpeg', 'Computer Accessories', 2),

                                                                                        -- Subcategories for Cameras & Photography (parent_id = 3)
                                                                                        (12, 'digital-cameras', '-3-', b'1', 'digital camera.png', 'Digital Cameras', 3),
                                                                                        (13, 'action-cameras', '-3-', b'1', 'action camera.jpg', 'Action Cameras', 3),
                                                                                        (14, 'camera-accessories', '-3-', b'1', 'camera accesories.jpg', 'Camera Accessories', 3),

                                                                                        -- Subcategories for Audio & Home Theater (parent_id = 4)
                                                                                        (15, 'headphones', '-4-', b'1', 'headphones.jpg', 'Headphones', 4),
                                                                                        (16, 'speakers', '-4-', b'1', 'audio system.jpg', 'Speakers', 4),
                                                                                        (17, 'audio-accessories', '-4-', b'1', 'mic.jpeg', 'Audio Accessories', 4),

                                                                                        -- Subcategories for Wearable Technology & Smart Devices (parent_id = 5)
                                                                                        (18, 'smartwatches', '-5-', b'1', 'smart watch.jpg', 'Smartwatches', 5),
                                                                                        (19, 'fitness-trackers', '-5-', b'1', 'fitness tracker.jpg', 'Fitness Trackers', 5),
                                                                                        (20, 'wearable-accessories', '-5-', b'1', 'wearable smart tech.jpeg', 'Wearable Accessories', 5);
