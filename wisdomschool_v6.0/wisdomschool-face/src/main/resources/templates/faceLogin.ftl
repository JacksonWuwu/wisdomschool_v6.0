<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>tracking.js</title>
    <script id="bfwone" data="dep=tracking-min|face-min|eye-min|mouth-min&err=0" type="text/javascript" src="//repo.bfw.wiki/bfwrepo/js/bfwone.js"></script>
    <script type="text/javascript">
        bready(function() {
            var img = document.getElementById('img');

            var tracker = new tracking.ObjectTracker(['face', 'eye', 'mouth']);
            tracker.setStepSize(1.7);

            tracking.track('#img', tracker);

            tracker.on('track', function(event) {
                event.data.forEach(function(rect) {
                    window.plot(rect.x, rect.y, rect.width, rect.height);
                });
            });

            window.plot = function(x, y, w, h) {
                var rect = document.createElement('div');
                document.querySelector('.demo-container').appendChild(rect);
                rect.classList.add('rect');
                rect.style.width = w + 'px';
                rect.style.height = h + 'px';
                rect.style.left = (img.offsetLeft + x) + 'px';
                rect.style.top = (img.offsetTop + y) + 'px';
            };
        });
    </script>
    <style>
        .rect {
            border: 2px solid #a64ceb;
            left: -1000px;
            position: absolute;
            top: -1000px;
        }

        #img {
            position: absolute;
            top: 50%;
            left: 50%;
            margin: -173px 0 0 -300px;
        }
    </style>
</head>
<body>
<div class="demo-frame">
    <div class="demo-container">
        <img id="img" src="../asset/man.png" />
    </div>
</div>

</body>
</html>
