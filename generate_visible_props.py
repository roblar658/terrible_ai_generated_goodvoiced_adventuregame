import os
import math
from PIL import Image, ImageDraw, ImageFilter

os.makedirs("assets/images/overlays", exist_ok=True)
os.makedirs("assets/images/portraits", exist_ok=True)
os.makedirs("assets/images/items", exist_ok=True)
os.makedirs("assets/images/ui", exist_ok=True)

print("Generating high-fidelity visible prop and character sprites...")

# 1. Old Torvald (Character sprite sitting on dock crate)
def make_torvald_prop():
    w, h = 220, 360
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    
    # Wooden crate base
    d.rectangle([30, 240, 190, 350], fill=(70, 50, 35, 255), outline=(120, 90, 60, 255), width=3)
    d.line([(30, 295), (190, 295)], fill=(50, 35, 25, 255), width=2)
    d.line([(30, 240), (190, 350)], fill=(90, 70, 45, 255), width=2)
    
    # Torvald's dark wool maritime coat and legs
    d.polygon([(60, 210), (160, 210), (170, 320), (135, 320), (110, 250), (85, 320), (50, 320)],
              fill=(25, 35, 48, 255), outline=(40, 55, 75, 255))
    
    # Yellow fisherman oilskin sou'wester hat & coat lapels
    d.polygon([(70, 130), (150, 130), (160, 220), (60, 220)], fill=(215, 165, 30, 255))
    
    # Weathered head & full grey fisherman beard
    d.ellipse([85, 80, 135, 135], fill=(210, 160, 125, 255))
    # Full bushy beard
    d.polygon([(82, 105), (138, 105), (120, 165), (100, 165)], fill=(185, 190, 195, 255))
    # Eyes & craggy face features
    d.ellipse([93, 96, 99, 102], fill=(30, 30, 30, 255))
    d.ellipse([121, 96, 127, 102], fill=(30, 30, 30, 255))
    
    # Yellow sou'wester hat brim & crown
    d.polygon([(55, 88), (165, 88), (145, 45), (75, 45)], fill=(225, 180, 35, 255))
    d.arc([55, 60, 165, 105], 0, 180, fill=(245, 205, 45, 255), width=8)
    
    # Wooden pipe in mouth with glowing ember
    d.line([(125, 125), (155, 118)], fill=(110, 75, 45, 255), width=4)
    d.rectangle([152, 106, 164, 122], fill=(135, 85, 50, 255))
    d.ellipse([154, 104, 162, 110], fill=(255, 120, 40, 255)) # Glowing cherry ember
    
    # Soft smoke puffs rising from pipe
    d.ellipse([160, 85, 175, 98], fill=(200, 210, 225, 90))
    d.ellipse([170, 65, 190, 80], fill=(200, 210, 225, 70))
    
    im.save("assets/images/overlays/torvald_prop.png", "PNG")
    print("Created torvald_prop.png")

make_torvald_prop()

# 2. Glowing Brass Kerosene Lantern
def make_lantern_prop():
    w, h = 140, 180
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    cx, cy = 70, 100
    
    # Ambient warm glow behind lantern
    for r in range(65, 20, -8):
        alpha = int(45 * (1 - r/65))
        d.ellipse([cx - r, cy - r, cx + r, cy + r], fill=(255, 200, 70, alpha))
    
    # Heavy brass base
    d.rectangle([35, 140, 105, 165], fill=(185, 140, 60, 255), outline=(235, 190, 95, 255), width=2)
    # Glass globe with glowing flame
    d.ellipse([42, 70, 98, 142], fill=(255, 240, 180, 210), outline=(210, 170, 80, 255), width=3)
    d.ellipse([54, 90, 86, 130], fill=(255, 190, 60, 255))
    d.ellipse([62, 98, 78, 122], fill=(255, 255, 230, 255)) # Bright white flame core
    # Brass cage protective bars
    d.line([(45, 80), (45, 140)], fill=(160, 120, 50, 255), width=3)
    d.line([(95, 80), (95, 140)], fill=(160, 120, 50, 255), width=3)
    # Brass hood and wire bail handle
    d.polygon([(38, 70), (102, 70), (85, 42), (55, 42)], fill=(195, 150, 65, 255), outline=(235, 195, 100, 255))
    d.arc([40, 15, 100, 55], 180, 360, fill=(195, 150, 65, 255), width=4)
    
    im.save("assets/images/overlays/lantern_prop.png", "PNG")
    print("Created lantern_prop.png")

make_lantern_prop()

# 3. Wrought Iron Gate with Raven Sigil
def make_gate_prop():
    w, h = 200, 320
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    
    # Heavy stone pillars on sides
    d.rectangle([0, 0, 28, h], fill=(45, 52, 65, 255), outline=(75, 88, 105, 255), width=2)
    d.rectangle([w - 28, 0, w, h], fill=(45, 52, 65, 255), outline=(75, 88, 105, 255), width=2)
    
    # Iron gate frame
    d.rectangle([28, 30, w - 28, h - 10], outline=(30, 34, 42, 255), width=6)
    # Vertical iron bars with spear finials
    for bx in range(48, w - 30, 24):
        d.line([(bx, 30), (bx, h - 10)], fill=(35, 40, 50, 255), width=4)
        d.polygon([(bx - 6, 30), (bx + 6, 30), (bx, 10)], fill=(45, 55, 70, 255))
        
    # Raven emblem shield in center
    cx, cy = 100, 160
    d.polygon([(cx - 40, cy - 35), (cx + 40, cy - 35), (cx + 40, cy + 20), (cx, cy + 55), (cx - 40, cy + 20)],
              fill=(25, 28, 35, 255), outline=(195, 155, 75, 255), width=3)
    # Raven silhouette inside shield
    d.polygon([(cx, cy - 25), (cx + 25, cy - 10), (cx + 15, cy + 15), (cx, cy + 30), (cx - 15, cy + 15), (cx - 25, cy - 10)],
              fill=(195, 155, 75, 255))
    
    # Heavy brass padlock
    d.rectangle([cx - 15, cy + 70, cx + 15, cy + 105], fill=(215, 165, 55, 255), outline=(140, 100, 35, 255), width=2)
    d.arc([cx - 11, cy + 50, cx + 11, cy + 80], 180, 360, fill=(180, 185, 195, 255), width=4)
    d.ellipse([cx - 3, cy + 82, cx + 3, cy + 92], fill=(20, 20, 20, 255))
    
    im.save("assets/images/overlays/gate_prop.png", "PNG")
    print("Created gate_prop.png")

make_gate_prop()

# 4. Boathouse Descent Stone Stairs & Wooden Sign
def make_boathouse_door_prop():
    w, h = 180, 220
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    
    # Stone archway receding down
    d.polygon([(20, 220), (20, 80), (90, 30), (160, 80), (160, 220)],
              fill=(15, 18, 25, 240), outline=(50, 60, 75, 255), width=4)
    # Stone steps
    for step in range(5):
        sy = 110 + step * 22
        d.polygon([(30 + step * 4, sy), (150 - step * 4, sy), (150 - step * 4, sy + 16), (30 + step * 4, sy + 16)],
                  fill=(35, 42, 54, 255), outline=(65, 78, 98, 255))
        
    # Weathered wooden sign hanging above
    d.rectangle([35, 20, 145, 55], fill=(85, 60, 38, 255), outline=(130, 95, 60, 255), width=2)
    d.line([(55, 0), (55, 20)], fill=(70, 75, 85, 255), width=2)
    d.line([(125, 0), (125, 20)], fill=(70, 75, 85, 255), width=2)
    
    # Subtile blue mist at base of stairs
    d.ellipse([25, 170, 155, 215], fill=(30, 90, 160, 80))
    
    im.save("assets/images/overlays/boathouse_door_prop.png", "PNG")
    print("Created boathouse_door_prop.png")

make_boathouse_door_prop()

# 5. Grandfather Clock (Visible antique timber case with swinging pendulum)
def make_clock_prop():
    w, h = 160, 340
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    cx = 80
    
    # Ornate wooden casing
    d.polygon([(20, 50), (cx, 15), (140, 50), (130, 330), (30, 330)],
              fill=(65, 40, 25, 255), outline=(125, 85, 50, 255), width=3)
    
    # Clock hood arch
    d.arc([20, 20, 140, 120], 180, 360, fill=(155, 105, 65, 255), width=4)
    
    # Brass dial plate
    d.ellipse([35, 55, 125, 145], fill=(238, 230, 205, 255), outline=(195, 150, 65, 255), width=4)
    # Hour numerals dots
    for angle in range(0, 360, 30):
        rad = math.radians(angle)
        dx = int(cx + math.sin(rad) * 36)
        dy = int(100 - math.cos(rad) * 36)
        d.ellipse([dx - 2, dy - 2, dx + 2, dy + 2], fill=(25, 25, 25, 255))
        
    # Clock hands stopped precisely at 03:17
    # Hour hand pointing at 3 (90 degrees)
    d.line([(cx, 100), (cx + 25, 100)], fill=(20, 20, 20, 255), width=4)
    # Minute hand pointing at 17 min (~102 degrees)
    d.line([(cx, 100), (cx + 12, 134)], fill=(20, 20, 20, 255), width=3)
    d.ellipse([cx - 4, 96, cx + 4, 104], fill=(185, 140, 55, 255))
    
    # Glass waist door with swinging brass pendulum
    d.rectangle([45, 160, 115, 310], fill=(25, 18, 14, 230), outline=(135, 95, 55, 255), width=2)
    d.line([(cx, 160), (cx + 8, 270)], fill=(195, 150, 65, 255), width=3)
    d.ellipse([cx - 10, 260, cx + 26, 296], fill=(225, 180, 75, 255), outline=(150, 110, 45, 255), width=2)
    
    im.save("assets/images/overlays/clock_prop.png", "PNG")
    print("Created clock_prop.png")

make_clock_prop()

# 6. Steel Wall Safe (Recessed vault door with dial)
def make_safe_prop():
    w, h = 130, 130
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    cx, cy = 65, 65
    
    # Steel frame
    d.rectangle([10, 10, 120, 120], fill=(42, 48, 58, 255), outline=(130, 145, 165, 255), width=4)
    # Heavy vault door hinges
    d.rectangle([112, 22, 122, 44], fill=(90, 105, 125, 255))
    d.rectangle([112, 86, 122, 108], fill=(90, 105, 125, 255))
    
    # Center combination dial
    d.ellipse([35, 35, 95, 95], fill=(30, 35, 45, 255), outline=(210, 175, 80, 255), width=3)
    for a in range(0, 360, 36):
        rad = math.radians(a)
        nx = int(cx + math.sin(rad) * 24)
        ny = int(cy - math.cos(rad) * 24)
        d.line([(cx, cy), (nx, ny)], fill=(150, 165, 185, 255), width=1)
    d.ellipse([50, 50, 80, 80], fill=(75, 85, 105, 255))
    d.rectangle([62, 36, 68, 50], fill=(235, 50, 50, 255)) # Red index mark
    
    # Vault turning handle
    d.line([(25, 75), (42, 75)], fill=(195, 155, 75, 255), width=5)
    d.ellipse([20, 70, 28, 80], fill=(225, 185, 95, 255))
    
    im.save("assets/images/overlays/safe_prop.png", "PNG")
    print("Created safe_prop.png")

make_safe_prop()

# 7. UNIT-7 Clockwork Automaton Sprite (Seated in maintenance rack)
def make_unit7_prop():
    w, h = 260, 380
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    cx = 130
    
    # Heavy iron maintenance scaffold arch
    d.arc([30, 20, 230, 140], 180, 360, fill=(85, 95, 110, 255), width=8)
    d.line([(34, 80), (34, 370)], fill=(75, 85, 100, 255), width=8)
    d.line([(226, 80), (226, 370)], fill=(75, 85, 100, 255), width=8)
    
    # Glowing vacuum tubes above automaton head
    for tx in [75, 105, 130, 155, 185]:
        d.rectangle([tx - 6, 25, tx + 6, 52], fill=(20, 45, 70, 220), outline=(130, 175, 220, 255))
        d.ellipse([tx - 3, 30, tx + 3, 46], fill=(120, 220, 255, 240)) # Glowing blue filament
        
    # Brass Automaton Torso & Armor Plates
    d.polygon([(85, 140), (175, 140), (185, 260), (75, 260)], fill=(175, 130, 55, 255), outline=(125, 90, 35, 255), width=3)
    
    # Automaton Head & Eyeglasses
    d.polygon([(95, 75), (165, 75), (170, 135), (130, 145), (90, 135)], fill=(195, 145, 60, 255), outline=(135, 95, 40, 255), width=3)
    # Dual glowing azure optical camera eyes
    d.ellipse([102, 95, 122, 115], fill=(90, 215, 255, 255), outline=(245, 255, 255, 255), width=2)
    d.ellipse([138, 95, 158, 115], fill=(90, 215, 255, 255), outline=(245, 255, 255, 255), width=2)
    # Speaker grille
    for gy in [124, 128, 132]:
        d.line([(114, gy), (146, gy)], fill=(45, 35, 20, 255), width=2)
        
    # Open chest clockwork aperture with spinning gear
    d.rectangle([98, 160, 162, 224], fill=(25, 20, 16, 255), outline=(215, 170, 75, 255), width=2)
    d.ellipse([110, 172, 150, 212], outline=(205, 160, 70, 255), width=4)
    d.ellipse([122, 184, 138, 200], fill=(100, 225, 255, 255)) # Pulsing heart crystal
    
    # Brass articulated arms & legs
    d.rectangle([55, 145, 80, 250], fill=(160, 115, 45, 255), outline=(115, 80, 30, 255), width=2)
    d.rectangle([180, 145, 205, 250], fill=(160, 115, 45, 255), outline=(115, 80, 30, 255), width=2)
    d.rectangle([85, 260, 120, 370], fill=(150, 105, 40, 255), outline=(110, 75, 25, 255), width=2)
    d.rectangle([140, 260, 175, 370], fill=(150, 105, 40, 255), outline=(110, 75, 25, 255), width=2)
    
    im.save("assets/images/overlays/unit7_prop.png", "PNG")
    print("Created unit7_prop.png")

make_unit7_prop()

# 8. Professor Astrid Ravn Character Sprite (Observatory)
def make_astrid_prop():
    w, h = 200, 360
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    cx = 100
    
    # Dark tailored Edwardian professor's coat and skirt
    d.polygon([(70, 110), (130, 110), (155, 350), (45, 350)], fill=(38, 28, 42, 255), outline=(65, 48, 72, 255), width=2)
    # Coat collar and bronze button row
    d.polygon([(82, 110), (100, 160), (118, 110)], fill=(215, 205, 190, 255))
    for by in range(175, 280, 25):
        d.ellipse([cx - 4, by, cx + 4, by + 8], fill=(210, 175, 75, 255))
        
    # Astrid's determined face
    d.ellipse([80, 48, 120, 96], fill=(232, 188, 158, 255))
    # Grey-streaked brown hair swept into a practical bun
    d.ellipse([70, 28, 130, 68], fill=(68, 62, 65, 255))
    d.line([(82, 38), (112, 38)], fill=(175, 175, 185, 255), width=2) # Grey streak
    
    # Golden round spectacles
    d.ellipse([84, 64, 98, 78], outline=(225, 190, 85, 255), width=2)
    d.ellipse([102, 64, 116, 78], outline=(225, 190, 85, 255), width=2)
    d.line([(98, 71), (102, 71)], fill=(225, 190, 85, 255), width=2)
    d.ellipse([88, 69, 93, 74], fill=(45, 75, 110, 255))
    d.ellipse([106, 69, 111, 74], fill=(45, 75, 110, 255))
    
    # Rolled parchment schematic held in hand
    d.rectangle([135, 190, 150, 250], fill=(235, 225, 195, 255), outline=(160, 140, 105, 255), width=2)
    
    im.save("assets/images/overlays/astrid_prop.png", "PNG")
    print("Created astrid_prop.png")

make_astrid_prop()

# 9. Captain's Ironbound Sea Chest (Shipwreck)
def make_chest_prop():
    w, h = 170, 130
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    
    # Weathered dark mahogany chest body
    d.rectangle([15, 45, 155, 120], fill=(65, 42, 28, 255), outline=(35, 22, 14, 255), width=3)
    # Curved chest lid
    d.arc([15, 15, 155, 75], 180, 360, fill=(80, 52, 35, 255), width=4)
    d.chord([15, 15, 155, 75], 180, 360, fill=(72, 48, 32, 255))
    
    # Iron strapping and rivets
    for bx in [28, 85, 142]:
        d.rectangle([bx - 6, 25, bx + 6, 120], fill=(45, 52, 62, 255))
        for ry in [35, 60, 85, 110]:
            d.ellipse([bx - 2, ry, bx + 2, ry + 4], fill=(130, 145, 160, 255))
            
    # Ornate celestial lock with azure gem
    d.rectangle([72, 65, 98, 95], fill=(215, 175, 65, 255), outline=(130, 95, 30, 255), width=2)
    d.ellipse([80, 74, 90, 84], fill=(80, 215, 255, 255)) # Glowing blue crystal keyhole
    
    im.save("assets/images/overlays/chest_prop.png", "PNG")
    print("Created chest_prop.png")

make_chest_prop()

# 10. Master Control Levers (Core Chamber Finale)
def make_levers_prop():
    w, h = 420, 150
    im = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    
    # Heavy bronze console base
    d.rectangle([10, 50, 410, 140], fill=(35, 42, 55, 255), outline=(180, 140, 70, 255), width=3)
    
    levers = [
        (85, "Silver", (205, 215, 230), "HARMONY"),
        (210, "Copper", (225, 130, 65), "SEAL"),
        (335, "Crystal", (85, 220, 255), "COSMOS")
    ]
    
    for lx, name, color, tag in levers:
        # Vertical guide track slot
        d.rectangle([lx - 12, 65, lx + 12, 130], fill=(15, 18, 24, 255))
        # Heavy mechanical lever arm
        d.line([(lx, 110), (lx, 25)], fill=(120, 130, 145, 255), width=7)
        # Large polished handle grip
        d.ellipse([lx - 16, 10, lx + 16, 42], fill=color, outline=(245, 250, 255, 255), width=3)
        # Engraved nameplate plaque
        d.rectangle([lx - 36, 116, lx + 36, 134], fill=(22, 26, 34, 255), outline=(160, 130, 60, 255), width=1)
        
    im.save("assets/images/overlays/levers_prop.png", "PNG")
    print("Created levers_prop.png")

make_levers_prop()

print("ALL VISIBLE PROPS AND OVERLAYS GENERATED SUCCESSFULLY!")
