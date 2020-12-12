import setuptools

setuptools.setup(
    name="pyama",
    version="1.0.0",
    author="Peter Verhas",
    author_email="peter@verhas.com",
    description="pyama snippet handling",
    long_description="# pyama document snippet handling tool",
    long_description_content_type="text/markdown",
    url="https://github.com/verhas/pyama",
    packages=setuptools.find_packages(),
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: Apache2",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.7',
)