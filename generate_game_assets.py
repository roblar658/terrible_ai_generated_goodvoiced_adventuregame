import os
import math
from PIL import Image, ImageDraw, ImageFilter, ImageFont

os.makedirs("assets/images/scenes", exist_ok=True)
os.makedirs("assets/images/overlays", exist_ok=True)
os.makedirs("assets/images/portraits", exist_ok=True)
os.makedirs("assets/images/items", exist_ok=True)
os.makedirs("assets/images/ui", exist_ok=True)

# Helper function to generate rich textured scenes
def create_atmospheric_scene(filename, theme):
    w, h = 1280, 720
    im = Image.new("RGB", (w, h))
    draw = ImageDraw.Draw(im)

    if theme == "boathouse":
        # Dark wooden interior with sea-water reflection and blue moonlight slashes
        for y in range(h):
            ratio = y / h
            # Deep timber wood tones to dark sea water below
            r = int(18 + 12 * (1 - ratio))
            g = int(22 + 18 * (1 - ratio) + 15 * ratio)
            b = int(32 + 30 * ratio)
            draw.line([(0, y), (w, y)], fill=(r, g, b))
        
        # Wooden ceiling rafters and vertical wall beams
        for x in range(0, w, 80):
            draw.rectangle([x, 0, x + 35, h], fill=(24, 28, 38))
            draw.line([(x, 0), (x, h)], fill=(40, 48, 62))
        
        # Slipway planks in perspective
        for i in range(12):
            y_pos = int(450 + i * 22)
            draw.polygon([(200 - i * 15, y_pos), (1080 + i * 15, y_pos),
                          (1080 + i * 15, y_pos + 12), (200 - i * 15, y_pos + 12)],
                         fill=(35, 42, 54))
        
        # Streaks of mystical blue light filtering through timber slats
        for sx in [240, 380, 520, 780, 950]:
            draw.polygon([(sx, 120), (sx + 25, 120), (sx + 160, 580), (sx + 90, 580)],
                         fill=(30, 80, 130))
        
        # Workbench silhouette on left
        draw.rectangle([60, 360, 340, 580], fill=(22, 26, 35), outline=(50, 60, 80))
        # Suspended chains & hoist pulley in center
        draw.line([(640, 0), (640, 380)], fill=(70, 80, 95), width=4)
        draw.ellipse([625, 380, 655, 410], fill=(90, 100, 120))

    elif theme == "cliff":
        # Sea cliffs with crashing sea, dark sky and glowing beacon
        for y in range(h):
            ratio = y / h
            if ratio < 0.5:
                # Night storm sky
                sky_r = int(12 + 10 * ratio)
                sky_g = int(18 + 15 * ratio)
                sky_b = int(36 + 25 * ratio)
                draw.line([(0, y), (w, y)], fill=(sky_r, sky_g, sky_b))
            else:
                # Dark sea with whitecaps
                sea_r = int(10 + 15 * (ratio - 0.5))
                sea_g = int(25 + 30 * (ratio - 0.5))
                sea_b = int(45 + 40 * (ratio - 0.5))
                draw.line([(0, y), (w, y)], fill=(sea_r, sea_g, sea_b))
        
        # Massive jagged cliff silhouette on right with lighthouse on top
        cliff_pts = [(700, h), (720, 480), (820, 380), (900, 240), (980, 180), (1280, 140), (1280, h)]
        draw.polygon(cliff_pts, fill=(18, 22, 30))
        
        # Lighthouse tower on cliff top
        draw.polygon([(1020, 180), (1070, 180), (1060, 70), (1030, 70)], fill=(28, 34, 46))
        # Glowing beacon
        draw.ellipse([1025, 45, 1065, 80], fill=(120, 220, 255))
        # Light cone slicing across sky
        draw.polygon([(1045, 62), (0, 140), (0, 240)], fill=(40, 120, 180))

        # Cliff path on left and suspension bridge cables
        draw.polygon([(0, 420), (320, 490), (380, h), (0, h)], fill=(22, 28, 38))
        draw.line([(320, 490), (740, 440)], fill=(90, 105, 125), width=3)
        draw.line([(320, 530), (740, 480)], fill=(70, 80, 95), width=2)
        
        # Runic archway on right
        draw.rectangle([780, 320, 820, 440], fill=(35, 44, 58))
        draw.rectangle([900, 300, 940, 420], fill=(35, 44, 58))
        draw.polygon([(770, 320), (950, 300), (950, 270), (770, 290)], fill=(45, 55, 75))

    elif theme == "staircase":
        # Spiral staircase in round stone lighthouse shaft
        for y in range(h):
            ratio = y / h
            r = int(15 + 15 * math.sin(ratio * math.pi))
            g = int(22 + 25 * math.sin(ratio * math.pi))
            b = int(35 + 45 * math.sin(ratio * math.pi))
            draw.line([(0, y), (w, y)], fill=(r, g, b))
        
        # Center massive vertical rotating copper drive shaft
        draw.rectangle([580, 0, 700, h], fill=(65, 45, 30), outline=(130, 90, 55))
        for y in range(0, h, 60):
            draw.ellipse([580, y, 700, y + 25], fill=(95, 68, 42))
        
        # Helical spiral iron staircase encircling the shaft
        for step in range(24):
            y_pos = int(step * 30)
            angle = step * 0.45
            x_offset = int(math.sin(angle) * 360) + 640
            width = int(140 + 40 * math.cos(angle))
            draw.rectangle([x_offset - width//2, y_pos, x_offset + width//2, y_pos + 10],
                           fill=(40, 48, 62), outline=(75, 90, 115))
            draw.line([x_offset + width//2, y_pos, x_offset + width//2, y_pos - 35],
                      fill=(90, 110, 140), width=2)
            
        # Electrical switchboard with glowing meters on left gallery
        draw.rectangle([100, 300, 320, 580], fill=(25, 32, 42), outline=(60, 75, 98))
        draw.rectangle([130, 340, 290, 420], fill=(15, 20, 28))
        for lamp_x, col in [(150, (255, 60, 60)), (190, (80, 255, 120)), (230, (80, 180, 255)), (270, (255, 220, 80))]:
            draw.ellipse([lamp_x, 360, lamp_x + 18, 378], fill=col)

    elif theme == "shipwreck":
        # Coastal cove at night with skeletal shipwreck hull against starry sky
        for y in range(h):
            ratio = y / h
            if ratio < 0.45:
                # Deep night celestial sky
                r = int(8 + 8 * ratio)
                g = int(12 + 12 * ratio)
                b = int(28 + 25 * ratio)
                draw.line([(0, y), (w, y)], fill=(r, g, b))
            else:
                # Black wet shoreline rocks and foam
                r = int(14 + 10 * (ratio - 0.45))
                g = int(22 + 18 * (ratio - 0.45))
                b = int(35 + 28 * (ratio - 0.45))
                draw.line([(0, y), (w, y)], fill=(r, g, b))
                
        # Constellation stars in sky (Orion & Cassiopeia)
        star_coords = [(200, 80), (220, 95), (240, 110), (190, 140), (250, 150), (215, 175), (230, 178),
                       (800, 60), (840, 75), (890, 65), (940, 85), (980, 70)]
        for sx, sy in star_coords:
            draw.ellipse([sx - 2, sy - 2, sx + 2, sy + 2], fill=(220, 240, 255))
            draw.line([(sx - 4, sy), (sx + 4, sy)], fill=(180, 210, 255))
            draw.line([(sx, sy - 4), (sx, sy + 4)], fill=(180, 210, 255))
            
        # Hull of the wrecked sailing ship "Nordstjernen"
        hull_pts = [(300, 550), (450, 320), (850, 340), (1050, 480), (950, 620), (400, 630)]
        draw.polygon(hull_pts, fill=(24, 28, 36), outline=(48, 56, 72))
        # Snapped timber mast angled toward the right
        draw.line([(600, 400), (820, 180)], fill=(38, 44, 56), width=12)
        # Tattered rigging ropes
        draw.line([(820, 180), (460, 330)], fill=(65, 75, 95), width=2)
        draw.line([(820, 180), (920, 450)], fill=(65, 75, 95), width=2)
        # Ironbound sea chest resting on deck
        draw.rectangle([540, 470, 660, 560], fill=(45, 35, 25), outline=(130, 110, 70))

    elif theme == "caverns":
        # Mystical crystal caverns with colossal glowing azure geodes
        for y in range(h):
            ratio = y / h
            r = int(8 + 12 * math.sin(ratio * math.pi))
            g = int(24 + 45 * math.sin(ratio * math.pi))
            b = int(48 + 80 * math.sin(ratio * math.pi))
            draw.line([(0, y), (w, y)], fill=(r, g, b))
            
        # Colossal glowing crystal pillars (azure, cyan, cobalt)
        crystals = [
            [(160, 640), (220, 200), (280, 640), (120, 200, 255), (60, 150, 220)],
            [(420, 680), (490, 280), (560, 680), (80, 180, 245), (40, 120, 200)],
            [(740, 660), (810, 160), (880, 660), (140, 230, 255), (70, 170, 240)],
            [(1020, 650), (1080, 240), (1140, 650), (100, 190, 250), (50, 130, 210)]
        ]
        for base_l, apex, base_r, col_hi, col_lo in crystals:
            draw.polygon([base_l, apex, ((base_l[0] + base_r[0])//2, base_l[1])], fill=col_hi)
            draw.polygon([((base_l[0] + base_r[0])//2, base_l[1]), apex, base_r], fill=col_lo)
            # Center facet reflection
            draw.line([apex, ((base_l[0] + base_r[0])//2, base_l[1])], fill=(240, 250, 255), width=3)
            
        # Acoustic resonance organ pipes integrated into stone wall
        for px in range(580, 700, 22):
            draw.rectangle([px, 300, px + 15, 520], fill=(120, 95, 55), outline=(180, 150, 90))
            
        # Reflective underground pool at bottom
        for y in range(560, h):
            ratio = (y - 560) / (h - 560)
            draw.line([(0, y), (w, y)], fill=(int(10 + 20*ratio), int(35 + 50*ratio), int(60 + 80*ratio)))

    elif theme == "observatory":
        # Celestial observatory at lighthouse summit with giant brass astrolabe
        for y in range(h):
            ratio = y / h
            r = int(12 + 10 * ratio)
            g = int(18 + 15 * ratio)
            b = int(35 + 30 * ratio)
            draw.line([(0, y), (w, y)], fill=(r, g, b))
            
        # Grand arched panoramic windows revealing tempest and stars
        for win_x in [120, 360, 600, 840, 1080]:
            draw.arc([win_x - 100, 80, win_x + 100, 480], 180, 360, fill=(60, 75, 100), width=6)
            draw.line([(win_x - 100, 280), (win_x - 100, 520)], fill=(60, 75, 100), width=6)
            draw.line([(win_x + 100, 280), (win_x + 100, 520)], fill=(60, 75, 100), width=6)
            
        # Massive spherical brass armillary astrolabe suspended from ceiling
        center = (640, 260)
        draw.ellipse([center[0] - 160, center[1] - 160, center[0] + 160, center[1] + 160],
                     outline=(175, 135, 65), width=8)
        draw.ellipse([center[0] - 140, center[1] - 70, center[0] + 140, center[1] + 70],
                     outline=(205, 165, 85), width=6)
        draw.line([(center[0], 0), (center[0], center[1] - 160)], fill=(120, 95, 45), width=6)
        
        # Central scientific control console with brass levers and dials
        draw.rectangle([340, 460, 940, 680], fill=(30, 38, 50), outline=(80, 100, 130))
        # Galvanometer dials
        for dx in range(400, 900, 100):
            draw.ellipse([dx, 490, dx + 65, 555], fill=(225, 215, 190), outline=(120, 90, 50))
            draw.line([(dx + 32, 522), (dx + 48, 506)], fill=(20, 20, 20), width=2)

    elif theme == "core":
        # Grand finale: Rotating Fresnel lens and pulsating azure core
        for y in range(h):
            ratio = y / h
            r = int(14 + 18 * math.sin(ratio * math.pi))
            g = int(32 + 55 * math.sin(ratio * math.pi))
            b = int(68 + 110 * math.sin(ratio * math.pi))
            draw.line([(0, y), (w, y)], fill=(r, g, b))
            
        # Fresnel lens concentric glass rings
        center = (640, 340)
        for radius in range(280, 60, -35):
            alpha_b = int(180 + radius * 0.25)
            draw.ellipse([center[0] - radius, center[1] - radius,
                          center[0] + radius, center[1] + radius],
                         outline=(100, 200, min(255, alpha_b)), width=6)
            
        # Intense luminous crystal core in the center
        draw.ellipse([center[0] - 70, center[1] - 70, center[0] + 70, center[1] + 70],
                     fill=(210, 245, 255))
        draw.ellipse([center[0] - 40, center[1] - 40, center[0] + 40, center[1] + 40],
                     fill=(255, 255, 255))
        
        # Radial energy rays slicing outwards
        for angle_deg in range(0, 360, 30):
            rad = math.radians(angle_deg)
            ex = int(center[0] + math.cos(rad) * 600)
            ey = int(center[1] + math.sin(rad) * 450)
            draw.line([center, (ex, ey)], fill=(120, 210, 255), width=3)
            
        # Grand control altar with the three master choices
        draw.rectangle([420, 540, 860, 720], fill=(28, 34, 46), outline=(90, 115, 150))
        # Three lever slots: Silver, Copper, Crystal
        for lx, col in [(480, (190, 200, 215)), (630, (215, 130, 60)), (780, (80, 220, 255))]:
            draw.rectangle([lx, 570, lx + 30, 670], fill=(15, 18, 25))
            draw.rectangle([lx + 6, 560, lx + 24, 630], fill=col, outline=(255, 255, 255))

    # Apply soft blur / bloom to give painterly cinematic feel
    im_smooth = im.filter(ImageFilter.GaussianBlur(1.2))
    # Blend slightly with original for sharpness
    final_im = Image.blend(im, im_smooth, 0.45)
    final_im.save(filename, quality=92)
    print(f"Created scene: {filename}")

# Generate the remaining 7 scenes
create_atmospheric_scene("assets/images/scenes/scene_02_boathouse.jpg", "boathouse")
create_atmospheric_scene("assets/images/scenes/scene_05_cliff.jpg", "cliff")
create_atmospheric_scene("assets/images/scenes/scene_06_staircase.jpg", "staircase")
create_atmospheric_scene("assets/images/scenes/scene_07_shipwreck.jpg", "shipwreck")
create_atmospheric_scene("assets/images/scenes/scene_08_caverns.jpg", "caverns")
create_atmospheric_scene("assets/images/scenes/scene_09_observatory.jpg", "observatory")
create_atmospheric_scene("assets/images/scenes/scene_10_core.jpg", "core")

print("All 10 scene images exist and are configured!")

# -------------------------------------------------------------
# Generate Interactive Overlay Items (RGBA with transparency)
# -------------------------------------------------------------
def create_overlay_item(filename, item_type):
    size = (180, 180)
    im = Image.new("RGBA", size, (0, 0, 0, 0))
    draw = ImageDraw.Draw(im)
    cx, cy = 90, 90

    if item_type == "lantern":
        # Antique brass kerosene lantern
        draw.rectangle([70, 70, 110, 130], fill=(35, 45, 60, 220), outline=(210, 160, 80, 255))
        # Glowing flame inside glass
        draw.ellipse([80, 85, 100, 115], fill=(255, 220, 110, 240))
        draw.ellipse([85, 92, 95, 108], fill=(255, 255, 220, 255))
        # Top cap and handle
        draw.polygon([(65, 70), (115, 70), (90, 48)], fill=(180, 130, 60, 255))
        draw.arc([68, 30, 112, 60], 180, 360, fill=(180, 130, 60, 255), width=3)
        # Base
        draw.rectangle([62, 130, 118, 142], fill=(160, 115, 50, 255))

    elif item_type == "rope":
        # Coiled maritime dock rope
        for r in range(50, 15, -10):
            draw.ellipse([cx - r, cy - r//2, cx + r, cy + r//2],
                         outline=(165, 135, 90, 255), width=8)

    elif item_type == "clock_face":
        # Grandfather clock brass ornate face with hands at 03:17
        draw.ellipse([30, 30, 150, 150], fill=(235, 225, 195, 240), outline=(190, 145, 65, 255), width=6)
        # Hour hand pointing at ~3
        draw.line([(cx, cy), (cx + 36, cy + 8)], fill=(20, 20, 20, 255), width=4)
        # Minute hand pointing at ~17 min (roughly 100 degrees)
        draw.line([(cx, cy), (cx + 15, cy + 45)], fill=(20, 20, 20, 255), width=3)

    elif item_type == "safe_dial":
        # Heavy vault door dial with numbered notches
        draw.ellipse([40, 40, 140, 140], fill=(45, 52, 65, 255), outline=(160, 175, 195, 255), width=5)
        draw.ellipse([70, 70, 110, 110], fill=(80, 92, 110, 255), outline=(210, 180, 100, 255), width=4)
        draw.rectangle([86, 42, 94, 62], fill=(230, 60, 60, 255)) # Dial indicator

    elif item_type == "automaton_chest":
        # Open brass gear heart on UNIT-7's chest
        draw.rectangle([45, 45, 135, 135], fill=(30, 24, 20, 255), outline=(215, 165, 75, 255), width=5)
        # Interlocking cogs
        draw.ellipse([60, 60, 105, 105], outline=(180, 140, 60, 255), width=6)
        draw.ellipse([90, 75, 130, 115], outline=(150, 110, 45, 255), width=5)
        # Glowing vacuum tube in center
        draw.ellipse([cx - 10, cy - 10, cx + 10, cy + 10], fill=(120, 220, 255, 255))

    elif item_type == "tuning_fork_pedestal":
        # Resonant acoustic tuning fork standing on pedestal
        draw.rectangle([78, 90, 102, 150], fill=(190, 200, 215, 255))
        # Two prongs
        draw.rectangle([70, 30, 82, 90], fill=(210, 220, 235, 255))
        draw.rectangle([98, 30, 110, 90], fill=(210, 220, 235, 255))
        # Blue acoustic glow shimmer
        draw.ellipse([55, 15, 125, 45], outline=(80, 200, 255, 180), width=3)

    elif item_type == "crystal_prism_socket":
        # Faceted azure prism resting in golden gimbal
        draw.polygon([(cx, 35), (145, cy), (cx, 145), (35, cy)],
                     fill=(90, 210, 255, 220), outline=(240, 250, 255, 255))
        draw.line([(cx, 35), (cx, 145)], fill=(255, 255, 255, 255), width=2)
        draw.line([(35, cy), (145, cy)], fill=(255, 255, 255, 255), width=2)

    im.save(filename, "PNG")
    print(f"Created overlay: {filename}")

create_overlay_item("assets/images/overlays/lantern_overlay.png", "lantern")
create_overlay_item("assets/images/overlays/rope_overlay.png", "rope")
create_overlay_item("assets/images/overlays/grandfather_clock_overlay.png", "clock_face")
create_overlay_item("assets/images/overlays/safe_overlay.png", "safe_dial")
create_overlay_item("assets/images/overlays/automaton_chest_overlay.png", "automaton_chest")
create_overlay_item("assets/images/overlays/tuning_fork_pedestal.png", "tuning_fork_pedestal")
create_overlay_item("assets/images/overlays/crystal_prism_socket.png", "crystal_prism_socket")

# -------------------------------------------------------------
# Generate Inventory Icons (64x64 or 96x96 clean icons)
# -------------------------------------------------------------
def create_inventory_icon(filename, item_name):
    size = (96, 96)
    im = Image.new("RGBA", size, (25, 32, 44, 230))
    draw = ImageDraw.Draw(im)
    draw.rectangle([2, 2, 93, 93], outline=(140, 115, 65, 255), width=2)

    cx, cy = 48, 48
    if item_name == "brass_key":
        # Ornate antique key with raven loop
        draw.ellipse([26, 26, 50, 50], outline=(225, 180, 75, 255), width=4)
        draw.line([(48, 48), (76, 76)], fill=(225, 180, 75, 255), width=5)
        draw.line([(68, 68), (74, 62)], fill=(225, 180, 75, 255), width=4)
        draw.line([(74, 74), (80, 68)], fill=(225, 180, 75, 255), width=4)

    elif item_name == "telegram":
        # Folded parchment with seal
        draw.rectangle([20, 26, 76, 70], fill=(235, 222, 195, 255), outline=(150, 130, 95, 255))
        for line_y in [36, 44, 52, 60]:
            draw.line([(28, line_y), (68, line_y)], fill=(120, 100, 70, 255), width=2)
        draw.ellipse([64, 56, 74, 66], fill=(185, 45, 45, 255)) # Red wax seal

    elif item_name == "lantern":
        draw.rectangle([38, 38, 58, 70], fill=(50, 60, 75, 255), outline=(210, 160, 80, 255), width=2)
        draw.ellipse([44, 46, 52, 62], fill=(255, 220, 110, 255))
        draw.polygon([(35, 38), (61, 38), (48, 26)], fill=(180, 130, 60, 255))

    elif item_name == "tuning_fork":
        draw.rectangle([45, 52, 51, 80], fill=(210, 225, 240, 255))
        draw.rectangle([38, 22, 44, 54], fill=(210, 225, 240, 255))
        draw.rectangle([52, 22, 58, 54], fill=(210, 225, 240, 255))
        draw.ellipse([32, 14, 64, 28], outline=(90, 215, 255, 200), width=2)

    elif item_name == "crystal_prism":
        draw.polygon([(48, 18), (78, 72), (18, 72)], fill=(100, 220, 255, 220), outline=(240, 250, 255, 255), width=2)
        draw.line([(48, 18), (48, 72)], fill=(255, 255, 255, 255), width=2)

    elif item_name == "punch_card":
        draw.rectangle([20, 28, 76, 68], fill=(225, 210, 175, 255), outline=(135, 115, 75, 255))
        for px in [30, 42, 54, 66]:
            for py in [36, 46, 56]:
                if (px + py) % 7 != 0:
                    draw.rectangle([px, py, px + 4, py + 4], fill=(45, 35, 25, 255))

    elif item_name == "wrench":
        draw.line([(28, 70), (66, 32)], fill=(160, 175, 195, 255), width=7)
        draw.ellipse([58, 24, 76, 42], outline=(160, 175, 195, 255), width=5)

    im.save(filename, "PNG")
    print(f"Created item icon: {filename}")

create_inventory_icon("assets/images/items/brass_key.png", "brass_key")
create_inventory_icon("assets/images/items/telegram.png", "telegram")
create_inventory_icon("assets/images/items/lantern.png", "lantern")
create_inventory_icon("assets/images/items/tuning_fork.png", "tuning_fork")
create_inventory_icon("assets/images/items/crystal_prism.png", "crystal_prism")
create_inventory_icon("assets/images/items/punch_card.png", "punch_card")
create_inventory_icon("assets/images/items/wrench.png", "wrench")

# -------------------------------------------------------------
# Generate Character Portraits (160x160 circular / framed avatar)
# -------------------------------------------------------------
def create_portrait(filename, character):
    size = (180, 180)
    im = Image.new("RGBA", size, (20, 25, 35, 255))
    draw = ImageDraw.Draw(im)
    cx, cy = 90, 90

    # Base background gradient ring
    draw.ellipse([8, 8, 172, 172], fill=(32, 42, 58, 255), outline=(175, 140, 75, 255), width=4)

    if character == "torvald":
        # Weathered fisherman with yellow oilskin sou'wester hat & beard
        # Shoulders/Coat
        draw.ellipse([35, 110, 145, 195], fill=(30, 45, 60, 255))
        # Face
        draw.ellipse([62, 58, 118, 120], fill=(215, 165, 130, 255))
        # Grey beard
        draw.polygon([(65, 95), (115, 95), (90, 140)], fill=(185, 190, 195, 255))
        # Eyes
        draw.ellipse([74, 82, 80, 88], fill=(30, 30, 30, 255))
        draw.ellipse([100, 82, 106, 88], fill=(30, 30, 30, 255))
        # Yellow fisherman sou'wester hat
        draw.polygon([(40, 72), (140, 72), (120, 35), (60, 35)], fill=(225, 180, 35, 255))
        draw.arc([40, 50, 140, 85], 0, 180, fill=(245, 205, 45, 255), width=8)
        # Pipe
        draw.line([(105, 108), (130, 102)], fill=(110, 75, 45, 255), width=4)
        draw.rectangle([128, 92, 138, 106], fill=(135, 85, 50, 255))

    elif character == "astrid":
        # Distinguished female professor/inventor with spectacles & coat
        draw.ellipse([35, 115, 145, 200], fill=(50, 35, 48, 255))
        # Face
        draw.ellipse([64, 52, 116, 112], fill=(230, 185, 155, 255))
        # Grey streaked dark hair in bun
        draw.ellipse([55, 34, 125, 78], fill=(70, 68, 75, 255))
        # Spectacles (golden round frames)
        draw.ellipse([70, 72, 86, 88], outline=(220, 185, 90, 255), width=2)
        draw.ellipse([94, 72, 110, 88], outline=(220, 185, 90, 255), width=2)
        draw.line([(86, 80), (94, 80)], fill=(220, 185, 90, 255), width=2)
        # Keen intelligent eyes
        draw.ellipse([76, 78, 80, 82], fill=(45, 75, 105, 255))
        draw.ellipse([100, 78, 104, 82], fill=(45, 75, 105, 255))

    elif character == "unit7":
        # Antique brass automaton face with glowing blue camera eyes
        draw.ellipse([35, 115, 145, 200], fill=(75, 55, 35, 255))
        # Brass metal skull plate
        draw.polygon([(60, 48), (120, 48), (128, 115), (90, 130), (52, 115)],
                     fill=(195, 150, 65, 255), outline=(130, 95, 40, 255))
        # Rivets on forehead
        for rx in [68, 78, 90, 102, 112]:
            draw.ellipse([rx - 2, 54, rx + 2, 58], fill=(95, 68, 25, 255))
        # Dual glowing azure optical lenses
        draw.ellipse([68, 76, 84, 92], fill=(90, 215, 255, 255), outline=(240, 250, 255, 255), width=2)
        draw.ellipse([96, 76, 112, 92], fill=(90, 215, 255, 255), outline=(240, 250, 255, 255), width=2)
        # Speaker grille on mouth
        for gy in [106, 112, 118]:
            draw.line([(76, gy), (104, gy)], fill=(45, 35, 20, 255), width=2)

    elif character == "eivind":
        # Young cartographer/mechanic protagonist
        draw.ellipse([35, 115, 145, 200], fill=(40, 52, 68, 255))
        draw.ellipse([64, 54, 116, 114], fill=(228, 180, 148, 255))
        # Brown wavy hair
        draw.ellipse([58, 36, 122, 75], fill=(75, 50, 32, 255))
        # Alert eyes
        draw.ellipse([74, 78, 80, 84], fill=(35, 45, 55, 255))
        draw.ellipse([100, 78, 106, 84], fill=(35, 45, 55, 255))

    im.save(filename, "PNG")
    print(f"Created portrait: {filename}")

create_portrait("assets/images/portraits/torvald.png", "torvald")
create_portrait("assets/images/portraits/astrid.png", "astrid")
create_portrait("assets/images/portraits/unit7.png", "unit7")
create_portrait("assets/images/portraits/eivind.png", "eivind")

# -------------------------------------------------------------
# Generate Custom Cursors & UI Elements
# -------------------------------------------------------------
def create_ui_icons():
    # 1. Cursor Arrow
    c_arrow = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    d = ImageDraw.Draw(c_arrow)
    d.polygon([(2, 2), (2, 24), (8, 18), (14, 28), (18, 26), (12, 16), (20, 16)],
              fill=(245, 245, 245, 255), outline=(20, 20, 20, 255))
    c_arrow.save("assets/images/ui/cursor_arrow.png", "PNG")

    # 2. Cursor Inspect (Magnifying Glass)
    c_inspect = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    d = ImageDraw.Draw(c_inspect)
    d.ellipse([4, 4, 20, 20], outline=(245, 215, 120, 255), width=3)
    d.ellipse([7, 7, 17, 17], fill=(80, 160, 240, 120))
    d.line([(18, 18), (28, 28)], fill=(190, 145, 65, 255), width=4)
    c_inspect.save("assets/images/ui/cursor_inspect.png", "PNG")

    # 3. Cursor Talk (Speech Bubble)
    c_talk = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    d = ImageDraw.Draw(c_talk)
    d.rounded_rectangle([2, 4, 26, 22], radius=5, fill=(245, 245, 245, 255), outline=(40, 60, 90, 255), width=2)
    d.polygon([(8, 22), (4, 30), (16, 22)], fill=(245, 245, 245, 255), outline=(40, 60, 90, 255))
    d.ellipse([8, 11, 10, 13], fill=(40, 60, 90, 255))
    d.ellipse([13, 11, 15, 13], fill=(40, 60, 90, 255))
    d.ellipse([18, 11, 20, 13], fill=(40, 60, 90, 255))
    c_talk.save("assets/images/ui/cursor_talk.png", "PNG")

    # 4. Cursor Hand
    c_hand = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    d = ImageDraw.Draw(c_hand)
    d.polygon([(10, 4), (16, 4), (16, 12), (22, 14), (22, 24), (8, 24), (6, 16), (10, 12)],
              fill=(235, 200, 160, 255), outline=(40, 30, 20, 255))
    c_hand.save("assets/images/ui/cursor_hand.png", "PNG")

    # 5. Hotspot Ping Glow Radar Icon (48x48)
    ping = Image.new("RGBA", (48, 48), (0, 0, 0, 0))
    d = ImageDraw.Draw(ping)
    d.ellipse([14, 14, 34, 34], fill=(255, 225, 90, 230), outline=(255, 255, 255, 255), width=2)
    d.ellipse([6, 6, 42, 42], outline=(255, 215, 60, 150), width=2)
    ping.save("assets/images/ui/ping_icon.png", "PNG")

    print("All UI cursor and ping icons generated!")

create_ui_icons()
print("SUCCESS: Complete visual asset pipeline executed successfully!")
